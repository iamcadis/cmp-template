package com.core.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.core.common.error.AppException
import com.core.presentation.data.AppError
import org.jetbrains.compose.resources.getString
import template.core.presentation.generated.resources.Res
import template.core.presentation.generated.resources.err_no_internet

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

internal fun String.annotatedLabel(required: Boolean, color: Color) = buildAnnotatedString {
    append(this@annotatedLabel)
    if (required) withStyle(style = SpanStyle(color = color)) { append("*") }
}