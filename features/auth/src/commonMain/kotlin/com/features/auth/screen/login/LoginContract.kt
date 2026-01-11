package com.features.auth.screen.login

import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState
import com.navigation.NavRoute

class LoginContract {
    @Immutable
    data class State(
        val email: String = "",
        val loading: Boolean = false,
        val password: String = "",
    ) : ViewState

    sealed interface Action : ViewAction {
        data class EmailChanged(val value: String) : Action
        data class PasswordChanged(val value: String) : Action
        data object OpenRegister : Action
        data object RequestLogin : Action
    }

    sealed interface Effect : ViewEffect {
        data class NavigateToHome(val route: NavRoute) : Effect
        data class NavigateToRegister(val route: NavRoute) : Effect
    }
}