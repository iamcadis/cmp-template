package com.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.core.ui.AppTheme
import com.core.ui.util.ScreenType
import com.core.ui.util.rememberScreenType

@Composable
fun ResponsiveGroup(
    rowGap: Dp = AppTheme.dimens.default,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    content: @Composable (Modifier) -> Unit
) {
    val screenType = rememberScreenType()
    if (screenType != ScreenType.Compact) {
        CompositionLocalProvider(value = LocalLayoutDirection provides layoutDirection) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = rowGap),
            ) {
                content(Modifier.weight(1f))
            }
        }
    } else {
        content(Modifier.fillMaxWidth())
    }
}