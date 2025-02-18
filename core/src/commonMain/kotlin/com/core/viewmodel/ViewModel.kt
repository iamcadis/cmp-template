package com.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ViewModel<State: ViewState, Event: ViewEvent, Effect: ViewEffect> : ViewModel() {
    private val initial: State by lazy { initializeState() }

    private val _state = MutableStateFlow(initial)
    val state: StateFlow<State> by lazy { createState() }

    private val _effects = Channel<Effect>(capacity = Channel.CONFLATED)
    val effect = _effects.receiveAsFlow()

    private fun createState() = _state.onStart {
        viewModelScope.launch { loadInitialData() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = initial
    )

    protected abstract fun initializeState(): State

    open suspend fun loadInitialData() {}

    open fun onEvent(event: Event) {}

    protected fun updateState(reduce: State.() -> State) {
        _state.update(function = reduce)
    }

    protected fun sendEffect(effect: Effect) {
        _effects.trySend(effect)
    }
}