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
    sealed interface CallApi: LoginAction, ViewAction.CallApi {
        data object Login: CallApi
    }

    sealed interface SideEffect: LoginAction, ViewAction.SideEffect {
        data object OpenRegister: SideEffect
    }

    sealed interface UpdateData: LoginAction, ViewAction.UpdateData {
        data class Email(val email: String): UpdateData
        data class Password(val password: String): UpdateData
    }
}

sealed interface LoginEffect: ViewEffect {
    data class NavigateToHome(val route: NavRoute): LoginEffect
    data class NavigateToRegister(val route: NavRoute): LoginEffect
}