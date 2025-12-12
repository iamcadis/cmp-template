package com.core.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

enum class ScreenSize {
    Compact,
    Medium,
    Expanded
}

@Composable
fun rememberScreenSize(): ScreenSize {
    val density = LocalDensity.current
    val windowSize = LocalWindowInfo.current.containerSize

    return remember(windowSize) {
        val widthDp = with(density) { windowSize.width.toDp() }
        when {
            widthDp < 600.dp -> ScreenSize.Compact
            widthDp < 840.dp -> ScreenSize.Medium
            else -> ScreenSize.Expanded
        }
    }
}

@Composable
fun rememberIsTablet(): Boolean {
    val screenSize = rememberScreenSize()

    return remember(screenSize) {
        screenSize != ScreenSize.Compact
    }
}