package com.core.presentation.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.core.presentation.theme.AppTheme

@Composable
fun CaptionText(
    value: String,
    color: Color = AppTheme.colors.surfaceVariant,
    modifier: Modifier = Modifier
) {
    Text(
        text = value,
        color = color,
        style = AppTheme.typography.bodySmall,
        modifier = modifier
    )
}