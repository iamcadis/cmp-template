package com.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import com.core.ui.data.ScreenConfig

interface ScreenProvider {
    fun setConfig(config: ScreenConfig)
}

val LocalScreenProvider = compositionLocalOf<ScreenProvider> {
    error("ScreenConfigProvider not found!")
}