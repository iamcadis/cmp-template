package com.features.home.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.presentation.base.BaseScreen
import com.core.presentation.util.LaunchedViewEffect
import com.navigation.LocalNavigator
import com.navigation.navigate
import com.resources.Res
import com.resources.home
import com.resources.my_profile
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen() {
    val navigator = LocalNavigator.current
    val viewModel = koinViewModel<HomeViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val error by viewModel.appError.collectAsStateWithLifecycle()

    LaunchedViewEffect(viewModel.effect) { effect ->
        when(effect) {
            is HomeEffect.NavigateToProfile -> {
                navigator.navigate(destination = effect.route)
            }
        }
    }

    BaseScreen(
        error = error,
        pageTitle = stringResource(Res.string.home),
        topBarActions = {
            IconButton(onClick = { viewModel.handleAction(action = HomeAction.OpenProfile) }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(Res.string.my_profile)
                )
            }
        }
    ) {
        HomeContent(state = state, onAction = viewModel::handleAction)
    }
}