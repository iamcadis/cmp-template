package com.compose.app

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.compose.app.ui.NavHost
import com.compose.app.util.BackPressHandler
import com.core.presentation.theme.AppTheme
import com.core.presentation.widget.ConfirmationDialogDefault

@Composable
fun App() {
    val navController = rememberNavController()
    var showConfirmation by rememberSaveable { mutableStateOf(false) }
    val backPressHandler = remember(navController) {
        BackPressHandler(navController) { showConfirmation = it }
    }

    AppTheme {
        Surface {
            NavHost(
                navController = navController,
                onBackPressed = backPressHandler.backPressed
            )

            if (showConfirmation) {
                ConfirmationDialogDefault.LeavePage(
                    onCancel = backPressHandler.cancelLeaving,
                    onConfirm = backPressHandler.confirmLeaving,
                )
            }
        }
    }
}