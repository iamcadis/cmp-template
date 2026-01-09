package com.compose.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.app.ui.NavigationHost
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.LaunchedViewEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val appViewModel = koinViewModel<AppViewModel>()
    val authenticated by appViewModel.userId.collectAsStateWithLifecycle()

    LaunchedViewEffect(appViewModel.effect) { effect ->
        when (effect) {
            AppContract.Effect.NavigateBack -> {}
        }
    }

    AppTheme {
        NavigationHost(
            authenticated = authenticated,
            onBackPressed = {
                appViewModel.handleAction(AppContract.Action.GoBack)
            }
        )
    }
}