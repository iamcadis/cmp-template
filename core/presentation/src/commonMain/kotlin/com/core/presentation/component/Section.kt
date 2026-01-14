package com.core.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.core.presentation.theme.AppTheme

@Composable
fun Section(
    title: String,
    modifier: Modifier = Modifier,
    caption: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        caption?.let {
            Text(
                text = it,
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colors.onSurfaceVariant,
                modifier = Modifier
                    .padding(top = AppTheme.dimens.extraSmall)
            )
        }
        content()
    }
}