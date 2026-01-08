package com.core.ui.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.core.ui.data.ScreenConfig

class ScaffoldState {
    var screenConfig by mutableStateOf(ScreenConfig())
        private set

    fun updateConfig(config: ScreenConfig) {
        screenConfig = config
    }
}

val LocalScaffoldState = staticCompositionLocalOf { ScaffoldState() }