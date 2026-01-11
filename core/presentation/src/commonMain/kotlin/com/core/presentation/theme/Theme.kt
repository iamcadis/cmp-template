package com.core.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.core.presentation.data.Dimension
import com.core.presentation.data.ExtendedColorScheme

@Composable
fun AppTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkMode) darkColorScheme else lightColorScheme
    val extendedColorScheme = if (isDarkMode) extendedDarkColorScheme else extendedLightColorScheme

    CompositionLocalProvider(value = LocalExtendedColorScheme provides extendedColorScheme) {
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
        get() = Dimension.Default
}
