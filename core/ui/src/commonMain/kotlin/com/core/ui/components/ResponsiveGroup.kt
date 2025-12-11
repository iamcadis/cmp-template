package com.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.core.ui.widget.rememberIsTablet

@Composable
fun ResponsiveGroup(
    rowGap: Dp = 16.dp,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    content: @Composable (Modifier) -> Unit
) {
    val isTablet = rememberIsTablet()

    if (isTablet) {
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