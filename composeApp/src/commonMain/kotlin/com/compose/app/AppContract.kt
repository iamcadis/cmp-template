package com.compose.app

import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState

object AppContract {

    @Immutable
    data class State(val askLeave: Boolean = false) : ViewState

    sealed class Action : ViewAction {
        data object GoBack : Action()
    }

    sealed class Effect : ViewEffect {
        data object NavigateBack : Effect()
    }
}