package com.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.core.ui.theme.model.Dimension
import com.core.ui.theme.model.ExtendedColorScheme
import com.core.ui.widget.ScreenSize
import com.core.ui.widget.rememberScreenSize

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) darkColorScheme else lightColorScheme
    val extended = if (darkTheme) extendedDark else extendedLight
    val screenSize = rememberScreenSize()
    val dimensions = remember(screenSize) {
        when(screenSize) {
            ScreenSize.Compact -> CompactDimension
            ScreenSize.Medium -> MediumDimension
            ScreenSize.Expanded -> ExpandedDimension
        }
    }

    CompositionLocalProvider(
        LocalDimens provides dimensions,
        LocalExtendedColorScheme provides extended
    ) {
        MaterialTheme(colorScheme = colors, content = content)
    }
}

object AppTheme {
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

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography
}
