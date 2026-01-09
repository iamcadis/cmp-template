package com.core.presentation.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.core.presentation.data.AppError
import com.core.presentation.data.ScreenConfig
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.widget.LoadingDialog

@Composable
fun BaseScreen(
    error: AppError? = null,
    askLeave: Boolean = false,
    pageTitle: String = "",
    showTopBar: Boolean = true,
    showLoading: Boolean = false,
    loadingText: String = "Please wait...",
    topBarActions: @Composable (RowScope.() -> Unit)? = null,
    floatingButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val scaffoldState = LocalScaffoldState.current

    LaunchedEffect(askLeave, pageTitle, showTopBar, topBarActions, floatingButton) {
        scaffoldState.updateConfig(
            config = ScreenConfig(
                askLeave = askLeave,
                pageTitle = pageTitle,
                showTopBar = showTopBar,
                topBarActions = topBarActions,
                floatingButton = floatingButton
            )
        )
    }

    content()

    if (showLoading) {
        LoadingDialog(text = loadingText)
    }
}