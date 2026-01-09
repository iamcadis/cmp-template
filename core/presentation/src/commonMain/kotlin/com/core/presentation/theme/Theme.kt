package com.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.core.presentation.data.Dimension
import com.core.presentation.data.ExtendedColorScheme
import com.core.presentation.data.ScreenType
import com.core.presentation.util.rememberScreenType

@Composable
fun AppTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    screenType: ScreenType = rememberScreenType(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkMode) darkColorScheme else lightColorScheme
    val extendedColorScheme = if (isDarkMode) extendedDarkColorScheme else extendedLightColorScheme

    val dimensions = remember(screenType) {
        when(screenType) {
            ScreenType.Compact -> CompactDimension
            ScreenType.Medium -> MediumDimension
            ScreenType.Expanded -> ExpandedDimension
        }
    }

    CompositionLocalProvider(
        LocalDimens provides dimensions,
        LocalExtendedColorScheme provides extendedColorScheme
    ) {
        MaterialTheme(colorScheme = colors) {
            Surface(content = content)
        }
    }
}

object AppTheme {
    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    val extendedColors: ExtendedColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalExtendedColorScheme.current

    val dimens: Dimension
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
}
