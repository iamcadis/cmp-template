package com.core.ui.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.common.error.ErrorProvider
import com.core.ui.provider.LocalScreenProvider
import com.core.ui.provider.ScreenConfig
import com.core.ui.widget.LoadingDialog

@Composable
fun <A : ViewAction, E : ViewEffect, S : ViewState> BaseScreen(
    viewModel: BaseViewModel<A, E, S>,
    pageTitle: String = "",
    showTopBar: Boolean = true,
    confirmOnLeave: Boolean = false,
    pageLoadingText: String = "Please wait...",
    errorProvider: ErrorProvider? = null,
    topBarActions: @Composable (RowScope.() -> Unit)? = null,
    floatingButton: @Composable (() -> Unit)? = null,
    onEffect: (effect: E) -> Unit = { },
    content: @Composable (state: S, dispatch: (A) -> Unit) -> Unit
){

    val state by viewModel.state.collectAsStateWithLifecycle()
    val screenProvider = LocalScreenProvider.current

    LaunchedEffect(pageTitle, showTopBar, confirmOnLeave) {
        screenProvider.setConfig(
            config = ScreenConfig(
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