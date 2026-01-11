package com.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.core.presentation.theme.AppTheme
import com.core.presentation.data.ScreenType
import com.core.presentation.util.rememberScreenType

@Composable
fun Group(
    rowGap: Dp = AppTheme.dimens.default,
    rowLayoutDirection: LayoutDirection = LayoutDirection.Ltr,
    content: @Composable (Modifier) -> Unit
) {
    val screenType = rememberScreenType()
    if (screenType != ScreenType.Compact) {
        CompositionLocalProvider(value = LocalLayoutDirection provides rowLayoutDirection) {
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