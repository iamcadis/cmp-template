package com.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.theme.DisTheme

@Composable
fun Section(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
        space = DisTheme.dimens.default,
        alignment = Alignment.Top
    ),
    header: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = verticalArrangement) {
        header?.let { it() }
        content()
        footer?.let { it() }
    }
}