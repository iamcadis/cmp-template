package com.features.auth.screen.login

import com.core.data.local.LocalStorage
import com.core.data.local.SecureStorage
import com.core.data.local.SecureStorage.Companion.storeAccessToken
import com.core.data.local.SecureStorage.Companion.storeRefreshToken
import com.core.data.remote.dto.LoginRequestDto
import com.core.data.usecase.LoginUseCase
import com.core.presentation.base.BaseViewModel
import com.navigation.NavRoute
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel (
    private val login: LoginUseCase,
    private val localStorage: LocalStorage,
    private val secureStorage: SecureStorage,
) : BaseViewModel<LoginState, LoginAction, LoginEffect>(
    initialState = LoginState()
) {
    override fun handleAction(action: LoginAction) {
        when(action) {
            is LoginAction.EmailChanged -> updateEmail(action.value)
            is LoginAction.PasswordChanged -> updatePassword(action.value)
            LoginAction.OpenRegister -> openRegisterPage()
            LoginAction.RequestLogin -> requestLogin()
        }
    }

    private fun updateEmail(value: String) {
        updateState { copy(email = value) }
    }

    private fun updatePassword(value: String) {
        updateState { copy(password = value) }
    }

    private fun openRegisterPage() {
        // Post effect register to navigate to register screen
    }

    private fun requestLogin() {
        val request = LoginRequestDto(email = currentState.email, password = currentState.password)

        updateState { copy(loading = true) }
        launchSafe(onRetry = ::requestLogin) {
            try {
                login(request).also {
                    localStorage.storeUserId(it.userId)
                    secureStorage.storeAccessToken(it.accessToken)
                    secureStorage.storeRefreshToken(it.refreshToken)
                    postEffect(effect = LoginEffect.NavigateToHome(route = NavRoute.Home))
                }
            } finally {
                updateState { copy(loading = false) }
            }
        }
    }
}