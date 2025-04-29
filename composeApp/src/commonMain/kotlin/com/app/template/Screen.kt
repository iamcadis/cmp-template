package com.app.template

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.viewmodel.BaseViewModel
import com.core.viewmodel.ViewAction
import com.core.viewmodel.ViewEffect
import com.core.viewmodel.ViewState
import com.theme.widgets.ErrorView
import com.theme.widgets.LoadingView

@Composable
inline fun <reified State: ViewState, Action: ViewAction, Effect: ViewEffect> Container(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel<State, Action, Effect>,
    noinline onLaunchEffect: (Effect) -> Unit,
    noinline content: @Composable (State) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle(initialValue = null)

    val errorAction: () -> Unit = {
        viewModel.retry()
        viewModel.clearError()
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { onLaunchEffect(it) }
    }

    AnimatedContent(modifier = modifier, targetState = error) { newError ->
        newError?.let { ErrorView(error = it, onAction = errorAction) } ?: content(state)
    }

    LoadingView(isLoading = state.loading)
}

