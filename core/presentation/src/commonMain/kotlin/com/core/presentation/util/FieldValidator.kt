package com.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import com.core.presentation.data.Translatable
import com.core.presentation.data.Validator
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun resolveValidatorMessage(validator: Validator): String {
    val resolvedArgs = validator.formatArgs.map { arg ->
        when (arg) {
            is Translatable.Resource -> stringResource(arg.res).lowercase()
            is Translatable.Raw -> arg.value
        }
    }

    return stringResource(validator.messageRes, *resolvedArgs.toTypedArray())
}

fun createValidators(
    vararg elements: Pair<String, PersistentList<Validator>>
): State<PersistentMap<String, PersistentList<Validator>>> {
    return derivedStateOf { persistentMapOf(*elements) }
}

fun Map<String, PersistentList<Validator>>.validate() = values.all { validators ->
    validators.all { !it.isError }
}