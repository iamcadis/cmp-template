package com.compose.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.core.data.local.LocalStorage
import com.core.presentation.base.BaseScreen
import com.navigation.LocalNavigator
import com.navigation.NavRoute
import com.navigation.navigate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.koin.compose.koinInject

@Composable
fun SplashScreen() {
    val local = koinInject<LocalStorage>()
    val navigator = LocalNavigator.current

    LaunchedEffect(Unit) {
        delay(timeMillis = 300)
        val route = if (local.userId.first() == null) {
            NavRoute.Login
        } else {
            NavRoute.Home
        }

        navigator.navigate(destination = route) {
            popUpTo(route = NavRoute.Splash) { inclusive = true }
            launchSingleTop = true
        }
    }

    BaseScreen(showTopBar = false) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}