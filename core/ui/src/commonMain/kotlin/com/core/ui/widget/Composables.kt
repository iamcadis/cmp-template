package com.core.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberIsTablet(threshold: Dp = 600.dp): Boolean {
    val density = LocalDensity.current
    val windowSize = LocalWindowInfo.current.containerSize

    return remember(windowSize, threshold) {
        with(density) {
            windowSize.width.toDp() > threshold
        }
    }
}
