package com.core.presentation.data

import androidx.compose.runtime.Stable

@Stable
data class AppError(val message: String, val action: () -> Unit)