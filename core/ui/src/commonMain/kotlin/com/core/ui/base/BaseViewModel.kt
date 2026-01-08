@file:OptIn(ExperimentalAtomicApi::class)

package com.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.ui.data.AppError
import com.core.ui.util.toAppError
import com.firebase.analytics.AnalyticsTracker
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import template.core.ui.generated.resources.Res
import template.core.ui.generated.resources.msg_no_internet
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

abstract class BaseViewModel<S : ViewState, A : ViewAction, E : ViewEffect>(
    initialState: S
) : ViewModel(), KoinComponent {

    protected val analytics by lazy {
        getKoin().getOrNull<AnalyticsTracker>() ?: NoOpAnalytics
    }

    protected open val screenName = this::class.simpleName
        ?.removeSuffix("ViewModel")
        ?: "Unknown"

    private var pendingRetryAction: (() -> Unit)? = null
    private val hasInitialDataLoaded = AtomicBoolean(false)

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state
        .onStart {
            // Load initial data only once when flow starts being collected
            if (hasInitialDataLoaded.compareAndSet(expectedValue = false, newValue = true)) {
                analytics.logScreen(screenName = screenName)
                loadInitialData()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = initialState
        )

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _appError = MutableStateFlow<AppError?>(null)
    val appError: StateFlow<AppError?> = _appError
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        sendError(throwable = throwable)
    }

    open fun handleAction(action: A) {}

    protected open fun loadInitialData() {}

    protected fun sendEffect(effect: E) {
        _effect.trySend(effect)
    }

    protected fun sendError(throwable: Throwable) {
        viewModelScope.launch {
            val newError = throwable.toAppError(
                noInternetMessage = getString(Res.string.msg_no_internet),
                onClearError = ::clearError,
                onRetry = ::retry
            )
            _appError.update { newError }
        }
    }

    protected fun updateState(transform: S.() -> S) {
        _state.update(transform)
    }

    protected fun launchSafe(
        onRetry: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            onRetry?.let { pendingRetryAction = it }
            block()
        }
    }

    fun retry(): Boolean {
        return pendingRetryAction?.let { action ->
            action()
            pendingRetryAction = null
            true
        } ?: false
    }

    fun clearError() {
        _appError.update { null }
    }

    override fun onCleared() {
        super.onCleared()
        _effect.close()
        pendingRetryAction = null
    }
}

object NoOpAnalytics : AnalyticsTracker {
    override fun logEvent(name: String, params: Map<String, Any?>) {}
    override fun logScreen(screenName: String) {}
}