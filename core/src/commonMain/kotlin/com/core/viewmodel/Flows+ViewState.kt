package com.core.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

interface ViewAction

interface ViewEffect

interface ViewState {
    val loading: Boolean
        get() = false
}

/**
 * Starts the upstream flow in a given [scope], suspends until the first value is emitted, and returns a _hot_
 * [StateFlow] of future emissions, sharing the most recently emitted value from this running instance of the upstream flow
 * with multiple downstream subscribers. See the [StateFlow] documentation for the general concepts of state flows.
 */
fun <T> Flow<T>.onlyOnceStateIn(
    scope: CoroutineScope,
    initialValue: T,
    stopTimeoutMillis: Long = 0,
    replayExpiration: Long = Long.MAX_VALUE,
) = stateIn(
    scope = scope,
    started = OnlyOnceWhileSubscribed(
        stopTimeout = stopTimeoutMillis,
        replayExpiration = replayExpiration,
    ),
    initialValue = initialValue,
)

/**
 * Returns a flow that invokes the given [onLoad] **before** this flow starts to be collected and
 * **after** the flow is completed or cancelled.
 */
inline fun <T> Flow<T>.onLoad(crossinline onLoad: (Boolean) -> Unit): Flow<T> {
    return this.onStart { onLoad(true) }.onCompletion { onLoad(false) }
}
