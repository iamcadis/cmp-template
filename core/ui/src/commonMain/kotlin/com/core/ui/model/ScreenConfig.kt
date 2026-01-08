package com.core.ui.model

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class ScreenConfig(
    val pageTitle: String = "",
    val showTopBar: Boolean = true,
    val confirmOnLeave: Boolean = false,
    val topBarActions: @Composable (RowScope.() -> Unit)? = null,
    val floatingButton: @Composable (() -> Unit)? = null,
) {

    companion object {
        val EMPTY = ScreenConfig()
    }
}