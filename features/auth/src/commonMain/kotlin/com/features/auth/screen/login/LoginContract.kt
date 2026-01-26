package com.features.auth.screen.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState
import com.navigation.NavRoute

@Immutable
data class LoginField(
    val username: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
)

@Immutable
data class LoginState(val field: LoginField = LoginField(), val loading: Boolean = false): ViewState

sealed interface LoginAction: ViewAction {
    data object Login: LoginAction
    data object OpenRegister: LoginAction
}

sealed interface LoginEffect: ViewEffect {
    data class NavigateToHome(val route: NavRoute): LoginEffect
    data class NavigateToRegister(val route: NavRoute): LoginEffect
}