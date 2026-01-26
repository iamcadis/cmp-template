package com.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.canGoBack(): Boolean {
    return previousBackStackEntry != null
}

fun NavController.navigate(
    destination: NavRoute,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}
) {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(route = destination, builder = navOptionsBuilder)
    }
}

fun NavController.navigateAsTop(destination: NavRoute) {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(route = destination) {
            popUpTo(route = NavRoute.Splash) { inclusive = true }
            launchSingleTop = true
        }
    }
}

