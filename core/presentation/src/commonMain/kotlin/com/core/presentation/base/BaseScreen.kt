package com.core.presentation.base

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.component.SnackbarType
import com.core.presentation.data.AppError
import com.core.presentation.data.ScreenConfig
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.widget.LoadingDialog

@Composable
fun BaseScreen(
    error: AppError? = null,
    pageTitle: String = "",
    showTopBar: Boolean = true,
    showLoading: Boolean = false,
    loadingText: String = "Please wait...",
    confirmOnBack: Boolean = false,
    topBarActions: @Composable (RowScope.() -> Unit)? = null,
    floatingButton: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val scaffoldState = LocalScaffoldState.current
    val snackbarHostState = LocalSnackbarHostState.current

    val currentTopBarActions by rememberUpdatedState(topBarActions)
    val currentFloatingButton by rememberUpdatedState(floatingButton)

    LaunchedEffect(pageTitle, showTopBar, confirmOnBack) {
        scaffoldState.updateConfig(
            config = ScreenConfig(
                pageTitle = pageTitle,
                showTopBar = showTopBar,
                confirmOnBack = confirmOnBack,
                topBarActions = currentTopBarActions,
                floatingButton = currentFloatingButton
            )
        )
    }

    LaunchedEffect(error) {
        if (error != null && !error.fullPage) {
            snackbarHostState.showSnackbar(message = error.message, snackType = SnackbarType.ERROR)
            error.clearError?.invoke()
        }
    }

    AnimatedContent(
        targetState = error != null && error.fullPage,
        label = "FullPageError"
    ) { isError ->
        if (isError) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(error?.message.orEmpty())
            }
        } else {
            content()
        }
    }

    if (showLoading) {
        LoadingDialog(text = loadingText)
    }
}