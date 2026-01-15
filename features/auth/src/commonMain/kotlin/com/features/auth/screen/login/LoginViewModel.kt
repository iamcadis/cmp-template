package com.features.auth.screen.login

import com.core.common.extension.onFinally
import com.core.data.repository.api.AuthRepository
import com.core.presentation.base.BaseViewModel
import com.core.presentation.base.ViewAction
import com.features.auth.mapper.toDto
import com.navigation.NavRoute
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel (
    private val authRepository: AuthRepository
) : BaseViewModel<LoginState, LoginAction, LoginEffect>(initialState = LoginState()) {

    override fun onCallApi(action: ViewAction.CallApi) = action.runAs<LoginAction.CallApi> {
        when(it) {
            LoginAction.CallApi.Login -> login()
        }
    }

    override fun onSideEffect(action: ViewAction.SideEffect) = action.runAs<LoginAction.SideEffect> {
        when(it) {
            LoginAction.SideEffect.OpenRegister -> navigateToRegisterScreen()
        }
    }

    override fun onUpdateData(action: ViewAction.UpdateData) = action.runAs<LoginAction.UpdateData> {
        when(it) {
            is LoginAction.UpdateData.Email -> updateState { copy(email = it.email) }
            is LoginAction.UpdateData.Password -> updateState { copy(password = it.password) }
        }
    }

    private fun login() {
        launchWithRetry(
            onRetry = { login() },
            onStart = { updateState { copy(loading = true) } }
        ) {
            authRepository.login(data = currentState.toDto())
                .onFailure(::sendError)
                .onFinally(::hideLoading)
                .onSuccess {
                    postEffect(effect = LoginEffect.NavigateToHome(route = NavRoute.Home))
                }
        }
    }

    private fun hideLoading() {
        updateState { copy(loading = false) }
    }

    private fun navigateToRegisterScreen() {
        postEffect(effect = LoginEffect.NavigateToRegister(route = NavRoute.Register))
    }
}