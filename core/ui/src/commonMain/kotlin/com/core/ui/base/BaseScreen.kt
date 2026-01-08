package com.core.ui.base

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.core.ui.data.ScreenConfig
import com.core.ui.util.LocalScaffoldState
import com.core.ui.widget.LoadingDialog

@Composable
fun BaseScreen(
    pageTitle: String = "",
    pageLoading: Boolean = false,
    loadingText: String = "Please wait...",
    confirmOnLeave: Boolean = false,
    showTopBar: Boolean = true,
    topBarActions: @Composable (RowScope.() -> Unit)? = null,
    floatingButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {

    val scaffoldState = LocalScaffoldState.current

    LaunchedEffect(pageTitle, showTopBar, confirmOnLeave, topBarActions, floatingButton) {
        scaffoldState.updateConfig(
            config = ScreenConfig(
                pageTitle = pageTitle,
                showTopBar = showTopBar,
                confirmOnLeave = confirmOnLeave,
                topBarActions = topBarActions,
                floatingButton = floatingButton
            )
        )
    }

    content()

    if (pageLoading) {
        LoadingDialog(text = loadingText)
    }
}