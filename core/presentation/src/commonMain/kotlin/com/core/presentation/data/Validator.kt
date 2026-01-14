package com.core.presentation.data

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

sealed class Translatable {
    data class Resource(val res: StringResource) : Translatable()
    data class Raw(val value: String) : Translatable()
}

@Immutable
data class Validator(
    val isError: Boolean,
    val messageRes: StringResource,
    val formatArgs: List<Translatable> = emptyList()
)