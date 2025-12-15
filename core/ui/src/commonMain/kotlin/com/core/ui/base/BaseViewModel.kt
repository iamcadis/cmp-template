@file:Suppress("unused")
package com.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.analytics.AnalyticsTracker
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
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
                analytics.logEvent(name = "open_page", params = mapOf("page" to screenName))
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

    private val _error = MutableSharedFlow<Throwable>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error = _error.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        sendError(error = exception)
    }

    open fun handleAction(action: A) {}

    protected open fun loadInitialData() {}

    protected fun updateState(transform: S.() -> S) {
        _state.update(transform)
    }

    protected fun sendEffect(effect: E) {
        _effect.trySend(effect)
    }

    protected open fun sendError(error: Throwable) {
        _error.tryEmit(error)
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

    override fun onCleared() {
        super.onCleared()
        _effect.close()
        pendingRetryAction = null
    }
}