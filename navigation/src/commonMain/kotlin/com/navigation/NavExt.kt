package com.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptionsBuilder
import kotlin.reflect.KClass

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

fun NavController.navigateAsTopNav(destination: NavRoute) {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(route = destination) {
            popUpTo(route = NavRoute.Home) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

fun NavBackStackEntry.isRouteInHierarchy(route: KClass<*>): Boolean {
    return this.destination.hierarchy.any { it.hasRoute(route) }
}