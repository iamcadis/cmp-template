package com.core.ui.provider

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

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

interface ScreenProvider {
    fun setConfig(config: ScreenConfig)
}

val LocalScreenProvider = compositionLocalOf<ScreenProvider> {
    error("ScreenConfigProvider not found!")
}