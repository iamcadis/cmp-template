package com.core.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.transformLatest

internal class OnlyOnceWhileSubscribed(
    private val stopTimeout: Long,
    private val replayExpiration: Long = Long.MAX_VALUE,
) : SharingStarted {

    private val hasCollected: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        require(stopTimeout >= 0) {
            "stopTimeout($stopTimeout ms) cannot be negative"
        }
        require(replayExpiration >= 0) {
            "replayExpiration($replayExpiration ms) cannot be negative"
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> =
        combine(hasCollected, subscriptionCount) { collected, counts ->
            collected to counts
        }.transformLatest { pair ->
            val (collected, count) = pair
            if (count > 0 && !collected) {
                emit(SharingCommand.START)
                hasCollected.value = true
            } else {
                delay(stopTimeout)
                if (replayExpiration > 0) {
                    emit(SharingCommand.STOP)
                    delay(replayExpiration)
                }
            }
        }
            .dropWhile { it != SharingCommand.START }
            .distinctUntilChanged()

    override fun toString(): String {
        val params = buildList(2) {
            if (stopTimeout > 0) add("stopTimeout=${stopTimeout}ms")
            if (replayExpiration < Long.MAX_VALUE) add("replayExpiration=${replayExpiration}ms")
        }
        return "SharingStarted.WhileSubscribed(${params.joinToString()})"
    }

    override fun equals(other: Any?) = other is OnlyOnceWhileSubscribed &&
            stopTimeout == other.stopTimeout && replayExpiration == other.replayExpiration

    override fun hashCode(): Int = stopTimeout.hashCode() * 31 + replayExpiration.hashCode()
}