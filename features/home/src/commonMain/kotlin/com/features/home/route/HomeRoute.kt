package com.features.home.route

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.features.home.screen.HomeScreen
import com.navigation.NavRoute
import com.navigation.navigate

fun NavGraphBuilder.homeRoute(navController: NavController) {
    composable<NavRoute.Home>(
        enterTransition = {
            fadeIn(animationSpec = tween(600))
        }
    ) {
        HomeScreen(onNavigateToPostDetails = {
            navController.navigate(destination = NavRoute.PostDetails(postId = it))
        })
    }
}