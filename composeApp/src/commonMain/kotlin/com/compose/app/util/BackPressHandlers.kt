package com.compose.app.util

import androidx.navigation.NavController

class BackPressHandlers(
    private val navController: NavController,
    private val showLeaveConfirmation: (Boolean) -> Unit
) {
    val backPressed: (Boolean) -> Unit = { needConfirmation ->
        if (needConfirmation) showLeaveConfirmation(true)
        else navController.popBackStack()
    }

    val cancelLeaving: () -> Unit = { showLeaveConfirmation(false) }

    val confirmLeaving: () -> Unit = {
        showLeaveConfirmation(false)
        navController.popBackStack()
    }
}