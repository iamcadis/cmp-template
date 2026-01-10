package com.features.home.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.features.home.screen.HomeScreen
import com.navigation.NavRoute
import com.navigation.navigate

fun NavGraphBuilder.homeRoute(navController: NavController) {
    composable<NavRoute.Home> {
        HomeScreen(onNavigateToPostDetails = {
            navController.navigate(destination = NavRoute.PostDetails(postId = it))
        })
    }
}