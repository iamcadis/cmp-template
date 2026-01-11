package com.features.auth.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.base.BaseScreen
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.LaunchedViewEffect
import com.navigation.LocalNavController
import com.navigation.navigate
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import template.features.auth.generated.resources.Res
import template.features.auth.generated.resources.login
import template.features.auth.generated.resources.login_asking_for_register
import template.features.auth.generated.resources.login_instruction
import template.features.auth.generated.resources.login_instruction_caption
import template.features.auth.generated.resources.register

@Composable
internal fun LoginScreen() {
    val navController = LocalNavController.current
    val viewModel = koinViewModel<LoginViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val error by viewModel.appError.collectAsStateWithLifecycle()

    LaunchedViewEffect(viewModel.effect) { effect ->
        when(effect) {
            is LoginContract.Effect.NavigateToHome -> {
                navController.navigate(destination = effect.route)
            }
            is LoginContract.Effect.NavigateToRegister -> {
                // Navigate to register screen
            }
        }
    }

    BaseScreen(
        error = error,
        showTopBar = false,
        showLoading = state.loading
    ) {
        LoginContent(state = state, onAction = viewModel::handleAction)
    }
}

@Composable
private fun LoginContent(
    state: LoginContract.State,
    onAction: (LoginContract.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = AppTheme.dimens.default),
        verticalArrangement = Arrangement.spacedBy(
            space = AppTheme.dimens.default,
            alignment = Alignment.CenterVertically
        )
    ) {
        Spacer(modifier = Modifier.height(height = AppTheme.dimens.default))
        Icon(
            imageVector = Icons.Filled.Shield,
            contentDescription = "App Logo",
            tint = AppTheme.colors.primary,
            modifier = Modifier.size(64.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(Res.string.login_instruction),
            style = AppTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(Res.string.login_instruction_caption),
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant,
            modifier = Modifier.offset(y = -(AppTheme.dimens.medium))
        )
        Spacer(modifier = Modifier.height(height = AppTheme.dimens.default))
        Button(
            onClick = {
                onAction(LoginContract.Action.RequestLogin)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(Res.string.login))
        }
        Row(
            modifier = Modifier
                .padding(bottom = AppTheme.dimens.default)
                .offset(y = -(AppTheme.dimens.default))
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.login_asking_for_register),
                style = AppTheme.typography.bodyMedium
            )
            TextButton(onClick = { onAction(LoginContract.Action.OpenRegister) }) {
                Text(text = stringResource(Res.string.register))
            }
        }
    }
}