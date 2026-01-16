package com.compose.app.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.navigation.LocalNavigator
import com.navigation.canGoBack

@Composable
actual fun NavBack(onBack: () -> Unit) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current

    BackHandler {
        if (navigator.canGoBack()) {
            onBack()
        } else {
            (context as? Activity)?.moveTaskToBack(true)
        }
    }
}