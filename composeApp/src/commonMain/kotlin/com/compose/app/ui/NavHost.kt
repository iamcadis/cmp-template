package com.compose.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.compose.app.allRoutes
import com.compose.app.util.BackPressHandlers
import com.core.presentation.component.CustomSnackbarHost
import com.core.presentation.component.CustomSnackbarHostState
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.util.ScaffoldState
import com.core.presentation.widget.ConfirmationDialogDefault
import com.navigation.LocalNavigator
import com.navigation.NavRoute
import com.navigation.canGoBack

@Composable
fun NavHost(scaffoldState: ScaffoldState) {
    val navController = rememberNavController()
    val snackbarHostState = remember { CustomSnackbarHostState() }
    val backPressHandlers = remember(navController) {
        BackPressHandlers(navController, scaffoldState::showLeaveConfirmation)
    }

    CompositionLocalProvider(value = LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                NavTopBar(
                    config = scaffoldState.screenConfig,
                    canGoBack = navController.canGoBack(),
                    onBackPressed = {
                        backPressHandlers.backPressed(scaffoldState.screenConfig.confirmOnBack)
                    }
                )
            },
            snackbarHost = {
                CustomSnackbarHost(state = snackbarHostState)
            },
            floatingActionButton = {
                scaffoldState.screenConfig.floatingButton?.invoke()
            },
        ) {

            BackHandler(enabled = navController.canGoBack()) {
                backPressHandlers.backPressed(scaffoldState.screenConfig.confirmOnBack)
            }

            CompositionLocalProvider(value = LocalNavigator provides navController) {
                NavHost(navController = navController, startDestination = NavRoute.Splash) {
                    allRoutes(navController = navController)
                }
            }

            if (scaffoldState.confirmOnBack) {
                ConfirmationDialogDefault.LeavePage(
                    data = scaffoldState.confirmationData,
                    onCancel = backPressHandlers.cancelLeaving,
                    onConfirm = backPressHandlers.confirmLeaving,
                )
            }
        }
    }
}