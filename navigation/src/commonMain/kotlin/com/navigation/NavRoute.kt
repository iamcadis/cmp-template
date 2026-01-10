package com.navigation

import kotlinx.serialization.Serializable

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