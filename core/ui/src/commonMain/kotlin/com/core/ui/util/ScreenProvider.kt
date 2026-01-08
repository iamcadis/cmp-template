package com.core.ui.util

import androidx.compose.runtime.compositionLocalOf
import com.core.ui.model.ScreenConfig

interface ScreenProvider {
    fun setConfig(config: ScreenConfig)
}

val LocalScreenProvider = compositionLocalOf<ScreenProvider> {
    error("ScreenConfigProvider not found!")
}

