package com.compose.app.util

import androidx.navigation.NavController

class BackPressHandlers(
    private val navController: NavController,
    private val setShowConfirmation: (Boolean) -> Unit
) {
    val backPressed: (Boolean) -> Unit = { needConfirmation ->
        if (needConfirmation) setShowConfirmation(true)
        else navController.popBackStack()
    }

    val cancelLeaving: () -> Unit = { setShowConfirmation(false) }

    val confirmLeaving: () -> Unit = {
        setShowConfirmation(false)
        navController.popBackStack()
    }
}