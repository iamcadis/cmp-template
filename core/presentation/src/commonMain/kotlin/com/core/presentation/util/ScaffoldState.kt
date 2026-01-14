@file:Suppress("USELESS_CAST")

package com.core.presentation.util

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.core.presentation.data.Confirmation
import com.core.presentation.data.ScreenConfig

@Stable
class ScaffoldState(
    initialConfirmOnBack: Boolean = false,
    initialConfirmationData: Confirmation = Confirmation.Default,
    initialScreenConfig: ScreenConfig = ScreenConfig()
) {
    var screenConfig by mutableStateOf(initialScreenConfig)
        private set

    var confirmOnBack by mutableStateOf(initialConfirmOnBack)
        private set

    var confirmationData by mutableStateOf(initialConfirmationData)
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

    companion object {
        val Saver: Saver<ScaffoldState, *> = listSaver(
            save = {
                listOf(it.confirmOnBack)
            },
            restore = {
                ScaffoldState(
                    initialConfirmOnBack = it[0] as Boolean,
                )
            }
        )
    }
}

val LocalScaffoldState = staticCompositionLocalOf { ScaffoldState() }