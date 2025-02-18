package com.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val primaryLight = Color(0xFF0E0F0F)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFF242424)
val onPrimaryContainerLight = Color(0xFF8C8B8B)
val secondaryLight = Color(0xFF5F5E5E)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFE5E2E1)
val onSecondaryContainerLight = Color(0xFF656464)
val tertiaryLight = Color(0xFF100F0F)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFF262424)
val onTertiaryContainerLight = Color(0xFF8F8B8A)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF93000A)
val backgroundLight = Color(0xFFFDF8F8)
val onBackgroundLight = Color(0xFF1C1B1B)
val surfaceLight = Color(0xFFFDF8F8)
val onSurfaceLight = Color(0xFF1C1B1B)
val surfaceVariantLight = Color(0xFFE0E3E3)
val onSurfaceVariantLight = Color(0xFF444748)
val outlineLight = Color(0xFF747878)
val outlineVariantLight = Color(0xFFC4C7C7)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF313030)
val inverseOnSurfaceLight = Color(0xFFF4F0EF)
val inversePrimaryLight = Color(0xFFC8C6C5)
val surfaceDimLight = Color(0xFFDDD9D8)
val surfaceBrightLight = Color(0xFFFDF8F8)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF7F3F2)
val surfaceContainerLight = Color(0xFFF1EDEC)
val surfaceContainerHighLight = Color(0xFFEBE7E7)
val surfaceContainerHighestLight = Color(0xFFE5E2E1)

val primaryDark = Color(0xFFC8C6C5)
val onPrimaryDark = Color(0xFF303030)
val primaryContainerDark = Color(0xFF242424)
val onPrimaryContainerDark = Color(0xFF8C8B8B)
val secondaryDark = Color(0xFFC9C6C5)
val onSecondaryDark = Color(0xFF313030)
val secondaryContainerDark = Color(0xFF474646)
val onSecondaryContainerDark = Color(0xFFB7B4B4)
val tertiaryDark = Color(0xFFCAC5C5)
val onTertiaryDark = Color(0xFF323030)
val tertiaryContainerDark = Color(0xFF262424)
val onTertiaryContainerDark = Color(0xFF8F8B8A)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF141313)
val onBackgroundDark = Color(0xFFE5E2E1)
val surfaceDark = Color(0xFF141313)
val onSurfaceDark = Color(0xFFE5E2E1)
val surfaceVariantDark = Color(0xFF444748)
val onSurfaceVariantDark = Color(0xFFC4C7C7)
val outlineDark = Color(0xFF8E9192)
val outlineVariantDark = Color(0xFF444748)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFE5E2E1)
val inverseOnSurfaceDark = Color(0xFF313030)
val inversePrimaryDark = Color(0xFF5F5E5E)
val surfaceDimDark = Color(0xFF141313)
val surfaceBrightDark = Color(0xFF3A3939)
val surfaceContainerLowestDark = Color(0xFF0E0E0E)
val surfaceContainerLowDark = Color(0xFF1C1B1B)
val surfaceContainerDark = Color(0xFF201F1F)
val surfaceContainerHighDark = Color(0xFF2B2A2A)
val surfaceContainerHighestDark = Color(0xFF353434)


/**
 * CompositionLocal used to pass [ColorScheme] down the tree.
 *
 * Setting the value here is typically done as part of [MaterialTheme]. To retrieve the current
 * value of this CompositionLocal, use [DisTheme.colors].
 */
internal val LocalColors = staticCompositionLocalOf { lightColorScheme() }