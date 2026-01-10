package com.feature.auth.route

sealed interface AuthRoute {
    data object Login : AuthRoute
    data object Profile : AuthRoute
}

