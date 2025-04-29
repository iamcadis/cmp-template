package com.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State: ViewState, Action: ViewAction, Effect: ViewEffect>(initial: State) : ViewModel() {
    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _error = Channel<Throwable?>(Channel.BUFFERED)
    val error = _error.receiveAsFlow()

    private val _state = MutableStateFlow(initial)
    val state: StateFlow<State> = _state
        .onStart { fetchInitialData() }
        .onlyOnceStateIn(
            scope = viewModelScope,
            initialValue = _state.value,
            stopTimeoutMillis = 5_000
        )

    private var retryAction: (() -> Unit)? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _error.trySend(exception)
    }

    open fun onAction(action: Action) {}

    protected open fun fetchInitialData() {}

    protected fun sendEffect(value: Effect) {
        viewModelScope.launch { _effect.send(value) }
    }

    protected fun updateState(transform: State.() -> State) {
        _state.update(function = transform)
    }

    protected fun launchWithRetry(
        onRetry: (() -> Unit)? = null,
        onAction: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            onRetry?.let { retryAction = it }
            onAction()
        }
    }

    fun retry() {
        retryAction?.invoke()
    }

    fun clearError() {
        _error.trySend(null)
    }
}