package com.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("CompositionLocal LocalNavController not present")
}

sealed interface NavRoute {
    @Serializable
    data object Splash : NavRoute

    @Serializable
    data object Login : NavRoute

    @Serializable
    data object Home : NavRoute

    @Serializable
    data object Profile : NavRoute

    @Serializable
    data class PostDetails(val postId: Int) : NavRoute
}