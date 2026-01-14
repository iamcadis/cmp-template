package com.compose.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.compose.app.allRoutes
import com.core.presentation.component.CustomSnackbarHost
import com.core.presentation.component.CustomSnackbarHostState
import com.core.presentation.component.LocalSnackbarHostState
import com.core.presentation.util.LocalScaffoldState
import com.core.presentation.util.ScaffoldState
import com.navigation.LocalNavigator
import com.navigation.NavRoute
import com.navigation.canGoBack

@Composable
fun NavHost(
    navController: NavHostController,
    onBackPressed: (showConfirmation: Boolean) -> Unit
) {
    val scaffoldState = remember { ScaffoldState() }
    val snackbarHostState = remember { CustomSnackbarHostState() }

    CompositionLocalProvider(
        LocalScaffoldState provides scaffoldState,
        LocalSnackbarHostState provides snackbarHostState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                NavTopBar(
                    config = scaffoldState.screenConfig,
                    canGoBack = navController.canGoBack(),
                    onBackPressed = {
                        onBackPressed(scaffoldState.screenConfig.confirmOnBack)
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
                onBackPressed(scaffoldState.screenConfig.confirmOnBack)
            }

            CompositionLocalProvider(value = LocalNavigator provides navController) {
                NavHost(navController = navController, startDestination = NavRoute.Splash) {
                    allRoutes(navController = navController)
                }
            }
        }
    }
}