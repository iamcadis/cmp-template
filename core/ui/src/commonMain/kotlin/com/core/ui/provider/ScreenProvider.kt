package com.core.ui.provider

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

@Stable
data class ScreenConfig(
    val pageTitle: String = "",
    val showTopBar: Boolean = true,
    val confirmOnLeave: Boolean = false,
    val topBarActions: @Composable (RowScope.() -> Unit)? = null,
    val floatingButton: @Composable (() -> Unit)? = null,
)

@Stable
interface ScreenProvider {
    fun setConfig(config: ScreenConfig)
}

val LocalScreenProvider = compositionLocalOf<ScreenProvider> {
    error("ScreenConfigProvider not found!")
}