package com.core.presentation.util

import androidx.compose.ui.Modifier
import com.core.common.error.AppException
import com.core.presentation.data.AppError
import com.resources.Res
import com.resources.err_no_internet
import org.jetbrains.compose.resources.getString

internal suspend fun Throwable.toAppError(onClear: () -> Unit, onRetry: () -> Unit) = when (this) {
    is AppException.NoInternet -> AppError(
        message = getString(Res.string.err_no_internet),
        fullPage = true,
        clearError = onClear,
        retryAction = onRetry
    )
    is AppException.Api -> AppError(
        message = error.getTranslatedMessage(),
        clearError = onClear,
    )
    else -> AppError(
        message = message ?: "An unknown error occurred",
        clearError = onClear,
    )
}

inline fun Modifier.applyIf(
    condition: Boolean,
    block: Modifier.() -> Modifier
): Modifier = if (condition) {
    then(block(Modifier))
} else {
    this
}
