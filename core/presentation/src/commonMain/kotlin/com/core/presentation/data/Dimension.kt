package com.core.presentation.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimension(
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val default: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val superLarge: Dp
) {

    companion object {
        internal val Default = Dimension(
            extraSmall = 4.dp,
            small = 8.dp,
            medium = 12.dp,
            default = 16.dp,
            large = 24.dp,
            extraLarge = 32.dp,
            superLarge = 48.dp
        )
    }
}