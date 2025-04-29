package com.app.template.home

import androidx.compose.runtime.Immutable
import com.core.viewmodel.ViewAction
import com.core.viewmodel.ViewEffect
import com.core.viewmodel.ViewState

class Home {
    @Immutable
    data class State(override val loading: Boolean, val id: Int): ViewState {
        companion object {
            val Initial = State(false, 0)
        }
    }

    sealed class Action: ViewAction {
        data class OpenDetails(val id: Long): Action()
    }

    sealed class Effect: ViewEffect {
        data class OpenDetails(val id: Long): Effect()
    }
}