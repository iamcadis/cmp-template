package com.core.ui.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.ui.provider.LocalScreenProvider
import com.core.ui.provider.ScreenConfig
import com.core.ui.widget.LoadingDialog

@Composable
fun <S : ViewState, A : ViewAction, E : ViewEffect> BaseScreen(
    viewModel: BaseViewModel<S, A, E>,
    pageTitle: String = "",
    showTopBar: Boolean = true,
    confirmOnLeave: Boolean = false,
    pageLoadingText: String = "Please wait...",
    topBarActions: @Composable (RowScope.() -> Unit)? = null,
    floatingButton: @Composable (() -> Unit)? = null,
    onEffect: (effect: E) -> Unit = { },
    content: @Composable (state: S, dispatch: (A) -> Unit) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val screenProvider = LocalScreenProvider.current

    SideEffect {
        screenProvider.setConfig(
            ScreenConfig(
                pageTitle = pageTitle,
                showTopBar = showTopBar,
                confirmOnLeave = confirmOnLeave,
                topBarActions = topBarActions,
                floatingButton = floatingButton
            )
        )
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect(collector = onEffect)
    }

    content(state, viewModel::handleAction)

    if (state.pageLoading) {
        LoadingDialog(text = pageLoadingText)
    }
}