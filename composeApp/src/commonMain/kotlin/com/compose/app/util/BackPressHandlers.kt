package com.compose.app.util

import androidx.compose.runtime.Stable
import androidx.navigation.NavController

@Stable
class BackPressHandlers(
    private val navigator: NavController,
    private val showLeaveConfirmation: (Boolean) -> Unit
) {
    val backPressed: (Boolean) -> Unit = { needConfirmation ->
        if (needConfirmation) showLeaveConfirmation(true)
        else navigator.popBackStack()
    }

    val cancelLeaving: () -> Unit = { showLeaveConfirmation(false) }

    val confirmLeaving: () -> Unit = {
        showLeaveConfirmation(false)
        navigator.popBackStack()
    }
}