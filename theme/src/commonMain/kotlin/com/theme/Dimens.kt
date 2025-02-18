package com.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimens (
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val extraMedium: Dp,
    val default: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val superLarge: Dp
)

val sw320Dimens = Dimens(
    extraSmall = 1.dp,
    small = 2.dp,
    medium = 4.dp,
    extraMedium = 6.dp,
    default = 8.dp,
    large = 12.dp,
    extraLarge = 16.dp,
    superLarge = 20.dp
)

val sw360Dimens = Dimens(
    extraSmall = 2.dp,
    small = 4.dp,
    medium = 8.dp,
    extraMedium = 12.dp,
    default = 16.dp,
    large = 24.dp,
    extraLarge = 28.dp,
    superLarge = 32.dp
)

val sw600Dimens = Dimens(
    extraSmall = 4.dp,
    small = 8.dp,
    medium = 12.dp,
    extraMedium = 16.dp,
    default = 20.dp,
    large = 28.dp,
    extraLarge = 36.dp,
    superLarge = 40.dp
)

val sw720Dimens = Dimens(
    extraSmall = 6.dp,
    small = 12.dp,
    medium = 16.dp,
    extraMedium = 20.dp,
    default = 24.dp,
    large = 32.dp,
    extraLarge = 40.dp,
    superLarge = 48.dp
)

/**
 * CompositionLocal used to pass [Dimens] down the tree.
 *
 * Setting the value here is typically done as part of [DisTheme]. To retrieve the current
 * value of this CompositionLocal, use [DisTheme.dimens].
 */
internal val LocalDimens = staticCompositionLocalOf { sw360Dimens }