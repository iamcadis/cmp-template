package com.theme.utilities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

data class Validator(val isError: Boolean, val message: String)

/**
 * Returns a derived state of the given elements.
 *
 * @param elements the elements to be validated
 * @return the derived state of the elements
 */
@Composable
fun addValidator(vararg elements: Pair<String, List<Validator>>) = remember(elements) {
    derivedStateOf { elements }
}

/**
 * Retrieves the list of validators associated with the given key.
 *
 * @param key the key to retrieve the validators from
 * @return the list of validators associated with the key, or null if the key is not found
 */
fun Array<out Pair<String, List<Validator>>>.getValue(key: String): List<Validator> {
    return this.firstOrNull { it.first == key }?.second ?: emptyList()
}

/**
 * Retrieves a boolean value indicating whether all validators associated with
 * the given key are valid.
 */
fun Collection<Pair<String, List<Validator>>>.isAllValid() = all { (_, validators) ->
    validators.all { !it.isError }
}

/**
 * Retrieves a validator object that contains information about the first has error
 */
fun List<Validator>.validate(): Validator {
    return this.firstOrNull { it.isError } ?: Validator(false, "")
}