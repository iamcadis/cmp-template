package com.core.presentation.data

import androidx.compose.runtime.Immutable

@Immutable
data class Validator(val isError: Boolean, val message: String) {
    companion object {
        val Empty = Validator(isError = false, message = "")
    }
}