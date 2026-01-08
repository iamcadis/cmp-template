package com.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import com.core.ui.data.ScreenConfig
import com.core.ui.widget.ConfirmationDialogDefault
import com.core.ui.component.CustomSnackbarHost
import com.core.ui.component.CustomSnackbarHostState
import com.core.ui.component.LocalSnackbarHostState

@Composable
fun AppContent(screenConfig: ScreenConfig, userHasLogin: Boolean) {
    val snackbarHost = remember { CustomSnackbarHostState() }
    var showLeaveConfirmation by rememberSaveable { mutableStateOf(false) }

    val goBackToPreviousScreen: () -> Unit = {
        showLeaveConfirmation = false
        // directly go back to previous screen
    }

    val dismissConfirmation: () -> Unit = {
        showLeaveConfirmation = false
    }

    val backPressHandler: () -> Unit = {
        if (screenConfig.confirmOnLeave) {
            showLeaveConfirmation = true
        } else {
            goBackToPreviousScreen()
        }
    }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHost) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                CustomSnackbarHost(state = snackbarHost)
            },
            topBar = {
                if (screenConfig.showTopBar) {
                    TopAppBar(
                        title = { Text(text = screenConfig.pageTitle) },
                        actions = { screenConfig.topBarActions?.let { it() } }
                    )
                }
            },
            floatingActionButton = {
                screenConfig.floatingButton?.let { it() }
            },
            content = { innerPadding ->
                BackHandler(onBack = backPressHandler)
                AppNavigation(modifier = Modifier.padding(paddingValues = innerPadding))

                if (showLeaveConfirmation) {
                    ConfirmationDialogDefault.LeavePage(
                        onCancel = dismissConfirmation,
                        onConfirm = goBackToPreviousScreen
                    )
                }
            }
        )
    }
}