package com.core.presentation.util

import com.core.common.error.AppException
import com.core.presentation.data.AppError

fun Throwable.toAppError(
    noInternetMessage: String,
    onClearError: () -> Unit,
    onRetry: () -> Unit
) = when (this) {
    is AppException.NoInternet -> AppError(
        message = noInternetMessage,
        action = {
            onRetry()
            onClearError()
        }
    )
    is AppException.Api -> AppError(
        message = error.message ?: "An unknown error occurred",
        action = onClearError
    )
    else -> AppError(
        message = message ?: "An unknown error occurred",
        action = onClearError
    )
}
