package com.compose.app.util

import androidx.navigation.NavController

class BackPressHandler(
    private val navController: NavController,
    private val setShowConfirmation: (Boolean) -> Unit
) {
    val backPressed: (Boolean) -> Unit = { askLeave ->
        if (askLeave) setShowConfirmation(true)
        else navController.popBackStack()
    }

    val cancelLeaving: () -> Unit = { setShowConfirmation(false) }

    val confirmLeaving: () -> Unit = {
        setShowConfirmation(false)
        navController.popBackStack()
    }
}