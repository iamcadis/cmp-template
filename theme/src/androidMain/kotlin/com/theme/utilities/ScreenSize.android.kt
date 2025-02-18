package com.theme.utilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
actual fun getScreenSize(): DpSize {
    val config = LocalConfiguration.current
    val smallestWidth = config.smallestScreenWidthDp
    return DpSize(width = config.screenWidthDp.dp, height = config.screenHeightDp.dp)
}