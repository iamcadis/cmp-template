package com.theme.utilities.ext

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf

/**
 * Filters the list based on the provided query and predicate, and returns a derived state.
 *
 * This function ensures that the filtering operation is efficiently tracked for recomposition,
 * updating the filtered list only when the query or predicate changes.
 *
 * @param query The query string used for filtering the list.
 * @param predicate A function that takes an item of type [T] and the query string,
 *                  returning a `Boolean` indicating whether the item should be included in the filtered list.
 * @return A `State` containing the filtered list, which automatically updates when the query or predicate changes.
 */
fun <T> List<T>.derivedFilterBy(query: String, predicate: (String, T) -> Boolean) = derivedStateOf {
    filter { predicate(query, it) }
}

/**
 * Adds an item to the lazy list only if the given [value] is not null.
 *
 * @param T The type of the non-null value.
 * @param value The optional value that determines whether the item should be added.
 * @param key An optional key for the item, useful for maintaining stable item positions.
 * @param content The composable content to be displayed for the non-null value.
 */
fun <T> LazyListScope.itemNotNull(
    value: T?,
    key: Any? = null,
    content: @Composable LazyItemScope.(T) -> Unit
) {
    value?.let { newValue ->
        item(key = key) {
            content(newValue)
        }
    }
}

/**
 * Adds an item to the lazy list only if the given [condition] is true.
 *
 * @param condition Determines whether the item should be added.
 * @param key An optional key for the item, useful for maintaining stable item positions.
 * @param content The composable content to be displayed if the condition is met.
 */
fun LazyListScope.itemShowIf(
    condition: Boolean,
    key: Any? = null,
    content: @Composable LazyItemScope.() -> Unit
) {
    if (condition) {
        item(key = key) {
            content()
        }
    }
}