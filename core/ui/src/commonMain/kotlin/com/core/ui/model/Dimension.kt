package com.core.ui.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

@Immutable
data class Dimension(
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val default: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val superLarge: Dp
)