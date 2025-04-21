package com.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ViewState

interface ViewEvent

interface ViewEffect

abstract class ViewModel<State: ViewState, Event: ViewEvent, Effect: ViewEffect> : ViewModel() {
    private val initial: State by lazy { createState() }

    private val _state = MutableStateFlow(initial)
    val state: StateFlow<State> by lazy { fetchData() }

    private val _effects = Channel<Effect>(capacity = Channel.BUFFERED)
    val effect = _effects.receiveAsFlow()

    private val _error = Channel<Exception>(capacity = Channel.BUFFERED)
    val error = _error.receiveAsFlow()

    private fun fetchData() = _state.onStart {
        fetchInitialData()
    }.onlyOnceStateIn(
        scope = viewModelScope,
        initialValue = initial,
        stopTimeoutMillis = 5_000
    )

    protected abstract fun createState(): State

    protected open fun fetchInitialData() {}

    open fun onEvent(event: Event) {}

    protected fun sendError(exception: Exception) {
        viewModelScope.launch { _error.send(exception) }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effects.send(effect) }
    }

    protected fun updateState(reduce: State.() -> State) {
        _state.update(function = reduce)
    }
}