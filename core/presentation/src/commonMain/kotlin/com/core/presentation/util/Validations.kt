package com.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.core.presentation.data.Validator
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun addValidators(
    vararg elements: Pair<String, PersistentList<Validator>>
) = remember(elements) {
    derivedStateOf { elements }
}

fun Array<out Pair<String, PersistentList<Validator>>>.getValue(key: String): PersistentList<Validator> {
    return this.firstOrNull { it.first == key }?.second ?: persistentListOf()
}

fun Array<out Pair<String, PersistentList<Validator>>>.isAllValid() = all { (_, validators) ->
    validators.all { !it.isError }
}