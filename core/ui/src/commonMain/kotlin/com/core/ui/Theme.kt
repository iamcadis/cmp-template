package com.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.core.ui.theme.CompactDimension
import com.core.ui.theme.ExpandedDimension
import com.core.ui.theme.LocalDimens
import com.core.ui.theme.LocalExtendedColorScheme
import com.core.ui.theme.MediumDimension
import com.core.ui.theme.darkColorScheme
import com.core.ui.theme.extendedDarkColorScheme
import com.core.ui.theme.extendedLightColorScheme
import com.core.ui.theme.lightColorScheme
import com.core.ui.data.Dimension
import com.core.ui.data.ExtendedColorScheme
import com.core.ui.data.ScreenType
import com.core.ui.util.rememberScreenType

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColorScheme else lightColorScheme
    val extendedColorScheme = if (darkTheme) extendedDarkColorScheme else extendedLightColorScheme

    val screenType = rememberScreenType()
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
