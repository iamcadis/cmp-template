package com.app

import androidx.compose.runtime.Stable
import com.core.ui.base.ViewAction
import com.core.ui.base.ViewEffect
import com.core.ui.base.ViewState

object AppContract {

    @Stable
    data class State(val hasLogin: Boolean = false) : ViewState

    sealed class Action : ViewAction {
        data object GoBack : Action()
    }

    sealed class Effect : ViewEffect {
        data object NavigateBack : Effect()
    }
}