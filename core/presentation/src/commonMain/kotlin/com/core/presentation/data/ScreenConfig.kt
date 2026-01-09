package com.core.presentation.data

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
data class ScreenConfig(
    val askLeave: Boolean = false,
    val pageTitle: String = "",
    val showTopBar: Boolean = true,
    val topBarActions: @Composable (RowScope.() -> Unit)? = null,
    val floatingButton: @Composable (() -> Unit)? = null,
)