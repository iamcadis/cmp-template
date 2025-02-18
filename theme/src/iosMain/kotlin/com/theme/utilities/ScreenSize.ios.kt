package com.theme.utilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenSize(): DpSize {
    val size = LocalWindowInfo.current.containerSize.toSize()
    val density = LocalDensity.current.density
    val width = (size.width / density).dp
    val height = (size.height / density).dp
    return DpSize(width = width, height = height)
}
