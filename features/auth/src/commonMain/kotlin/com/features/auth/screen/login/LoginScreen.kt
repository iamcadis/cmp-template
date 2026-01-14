package com.features.auth.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.base.BaseScreen
import com.core.presentation.util.LaunchedViewEffect
import com.navigation.LocalNavigator
import com.navigation.navigate
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginScreen() {
    val navController = LocalNavigator.current
    val viewModel = koinViewModel<LoginViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val error by viewModel.appError.collectAsStateWithLifecycle()

    LaunchedViewEffect(viewModel.effect) { effect ->
        when(effect) {
            is LoginEffect.NavigateToHome -> {
                navController.navigate(destination = effect.route)
            }
            is LoginEffect.NavigateToRegister -> {
                // Navigate to register screen
            }
        }
    }

    BaseScreen(error = error, showTopBar = false, showLoading = state.loading) {
        LoginContent(state = state, onAction = viewModel::handleAction)
    }
}