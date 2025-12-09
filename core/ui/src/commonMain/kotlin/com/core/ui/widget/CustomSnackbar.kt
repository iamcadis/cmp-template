package com.core.ui.widget

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

enum class SnackbarType {
    INFO, ERROR, SUCCESS
}

@Stable
class CustomSnackbarHostState {
    val hostState = SnackbarHostState()
    var currentType by mutableStateOf(SnackbarType.INFO)
        private set

    suspend fun showSnackbar(
        message: String,
        snackType: SnackbarType = SnackbarType.INFO,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    ): SnackbarResult {
        currentType = snackType

        return hostState.showSnackbar(
            message = message,
            actionLabel= actionLabel,
            withDismissAction = withDismissAction,
            duration = duration
        )
    }
}

@Composable
fun CustomSnackbarHost(
    state: CustomSnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(hostState = state.hostState, modifier = modifier) { data ->
        val (containerColor, contentColor) = when (state.currentType) {
            SnackbarType.INFO -> SnackbarDefaults.color to SnackbarDefaults.contentColor
            SnackbarType.ERROR -> Color(0xFFEB5757) to Color.White
            SnackbarType.SUCCESS -> Color(0xFF27AE60) to Color.White
        }

        Snackbar(
            snackbarData = data,
            containerColor = containerColor,
            contentColor = contentColor
        )
    }
}

val LocalSnackbarHostState = compositionLocalOf<CustomSnackbarHostState> {
    error("SnackbarHostState not found!")
}