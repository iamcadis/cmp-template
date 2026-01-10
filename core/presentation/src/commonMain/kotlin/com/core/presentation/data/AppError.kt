package com.core.presentation.data

import androidx.compose.runtime.Stable

@Stable
data class AppError(
    val message: String,
    val fullPage: Boolean = false,
    val clearError: (() -> Unit)? = null,
    val retryAction: (() -> Unit)? = null
)