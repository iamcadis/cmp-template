package com.core.presentation.util

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.core.presentation.data.ScreenConfig

@Stable
class ScaffoldState {
    var screenConfig by mutableStateOf(ScreenConfig())
        private set

    var confirmOnBack by mutableStateOf(false)
        private set

    fun updateConfig(config: ScreenConfig) {
        screenConfig = config
    }

    fun setShowConfirmation(show: Boolean) {
        confirmOnBack = show
    }
}

val LocalScaffoldState = compositionLocalOf { ScaffoldState() }