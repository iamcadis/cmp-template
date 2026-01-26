package com.compose.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.backhandler.BackHandler

@Composable
actual fun NavBack(canGoBack: () -> Boolean, onNavigateBack: () -> Unit) {
    BackHandler {
        if (canGoBack()) {
            onNavigateBack()
        }
    }
}