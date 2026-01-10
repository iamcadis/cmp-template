package com.compose.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.compose.app.ui.SplashScreen
import com.features.auth.route.authRoute
import com.features.home.route.homeRoute
import com.navigation.NavRoute

fun NavGraphBuilder.allRoutes(navController: NavController) {
    composable<NavRoute.Splash>(
        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { fadeOut(tween(300)) },
        popEnterTransition = { fadeIn(tween(300)) },
        popExitTransition = { fadeOut(tween(300)) }
    ) {
        SplashScreen()
    }

    authRoute(navController = navController)
    homeRoute(navController = navController)
}