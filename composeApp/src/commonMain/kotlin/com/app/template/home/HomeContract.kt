package com.app.template.home

import androidx.compose.runtime.Immutable
import com.core.viewmodel.ViewEffect
import com.core.viewmodel.ViewEvent
import com.core.viewmodel.ViewState

class HomeContract {
    @Immutable
    data class State(val id: Int): ViewState

    sealed class Event: ViewEvent

    sealed class Effect: ViewEffect
}