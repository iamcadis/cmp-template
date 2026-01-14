package com.core.presentation.util

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.core.presentation.data.Confirmation
import com.core.presentation.data.ScreenConfig

@Stable
class ScaffoldState {
    var screenConfig by mutableStateOf(ScreenConfig())
        private set

    var confirmOnBack by mutableStateOf(false)
        private set

    var confirmationData by mutableStateOf(Confirmation.Default)
        private set

    fun updateConfig(config: ScreenConfig) {
        screenConfig = config
    }

    fun showLeaveConfirmation(show: Boolean) {
        confirmOnBack = show
    }

    fun setBackConfirmationData(confirmation: Confirmation) {
        confirmationData = confirmation
    }
}

val LocalScaffoldState = compositionLocalOf { ScaffoldState() }