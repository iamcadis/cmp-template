package com.core.ui.data

import androidx.compose.runtime.Stable

@Stable
data class AppError(val message: String, val action: () -> Unit)