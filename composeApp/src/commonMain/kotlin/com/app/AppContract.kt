package com.app

import androidx.compose.runtime.Immutable
import com.core.ui.base.ViewAction
import com.core.ui.base.ViewEffect
import com.core.ui.base.ViewState

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