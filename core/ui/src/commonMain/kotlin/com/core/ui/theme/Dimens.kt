package com.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import com.core.ui.theme.model.Dimension

internal val CompactDimension = Dimension(
    extraSmall = 4.dp,
    small = 8.dp,
    medium = 12.dp,
    default = 16.dp,
    large = 24.dp,
    extraLarge = 32.dp,
    superLarge = 48.dp
)

internal val MediumDimension = Dimension(
    extraSmall = 4.dp,
    small = 8.dp,
    medium = 16.dp,
    default = 24.dp,
    large = 32.dp,
    extraLarge = 48.dp,
    superLarge = 64.dp
)

internal val ExpandedDimension = Dimension(
    extraSmall = 8.dp,
    small = 12.dp,
    medium = 24.dp,
    default = 32.dp,
    large = 48.dp,
    extraLarge = 64.dp,
    superLarge = 96.dp
)

internal val LocalDimens = staticCompositionLocalOf { CompactDimension }