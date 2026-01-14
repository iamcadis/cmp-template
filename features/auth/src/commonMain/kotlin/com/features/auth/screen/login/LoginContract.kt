package com.features.auth.screen.login

import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState
import com.navigation.NavRoute

@Immutable
data class LoginState(
    val email: String = "",
    val loading: Boolean = false,
    val password: String = "",
) : ViewState

sealed interface LoginAction: ViewAction {
    data class EmailChanged(val value: String): LoginAction
    data class PasswordChanged(val value: String): LoginAction
    data object OpenRegister: LoginAction
    data object RequestLogin: LoginAction
}

sealed interface LoginEffect: ViewEffect {
    data class NavigateToHome(val route: NavRoute): LoginEffect
    data class NavigateToRegister(val route: NavRoute): LoginEffect
}