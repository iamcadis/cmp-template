package com.compose.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.compose.app.ui.SplashScreen
import com.core.presentation.base.BaseScreen
import com.features.auth.route.authRoute
import com.features.home.route.homeRoute
import com.navigation.NavRoute

fun NavGraphBuilder.buildNavigationRoutes() {
    composable<NavRoute.Splash>(
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        SplashScreen()
    }

    composable<NavRoute.Test> {
        BaseScreen(confirmOnBack = true) {
            Text("TEST SCREEN")
        }
    }

    authRoute()
    homeRoute()
}