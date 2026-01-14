package com.features.auth.route

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.features.auth.screen.login.LoginScreen
import com.features.auth.screen.profile.ProfileScreen
import com.navigation.NavRoute

fun NavGraphBuilder.authRoute(navController: NavController) {
    composable<NavRoute.Login>(
        enterTransition = {
            fadeIn(animationSpec = tween(600))
        }
    ) {
        LoginScreen()
    }
    composable<NavRoute.Profile> {
        ProfileScreen()
    }
}