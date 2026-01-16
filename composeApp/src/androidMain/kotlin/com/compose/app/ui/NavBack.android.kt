package com.compose.app.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun NavBack(canGoBack: () -> Boolean, onNavigateBack: () -> Unit) {
    val context = LocalContext.current

    BackHandler {
        if (canGoBack()) {
            onNavigateBack()
        } else {
            (context as? Activity)?.moveTaskToBack(true)
        }
    }
}