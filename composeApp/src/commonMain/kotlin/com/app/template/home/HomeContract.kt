package com.app.template.home

import androidx.compose.runtime.Immutable
import com.core.mvi.ViewEffect
import com.core.mvi.ViewEvent
import com.core.mvi.ViewState

class HomeContract {
    @Immutable
    data class State(val loading: Boolean): ViewState

    sealed class Event: ViewEvent

    sealed class Effect: ViewEffect
}