package com.theme.utilities

import androidx.compose.foundation.clickable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

/**
 * Adds a required indicator (*) to a string if `isShow` is true.
 *
 * @param isShow Determines whether the required indicator should be added.
 * @param color The color of the required indicator.
 * @return A styled string with an asterisk (*) if required.
 */
fun String.addIndicatorRequired(isShow: Boolean, color: Color) = buildAnnotatedString {
    append(this@addIndicatorRequired)

    if (isShow && this@addIndicatorRequired.isNotEmpty()) {
        withStyle(style = SpanStyle(color = color)) {
            append("*")
        }
    }
}

/**
 * Checks if the current [Modifier] chain contains a `clickable` modifier.
 *
 * This function iterates through the modifier chain and looks for a `ModifierNodeElement`
 * with a name fallback of "clickable", which is how the `clickable` modifier is internally represented.
 *
 * @return `true` if the modifier contains a `clickable` element, `false` otherwise.
 */
fun Modifier.hasClickable(): Boolean {
    return this.foldIn(false) { _, element ->
        (element as? ModifierNodeElement<*>)?.nameFallback == "clickable"
    }
}

/**
 * Creates a [clickable] modifier without a ripple effect.
 *
 * @param enabled If false, the click action is disabled.
 * @param onClick The action to perform when clicked.
 */
fun Modifier.clickableWithoutRipple(enabled: Boolean = true, onClick: () -> Unit): Modifier {
    return this.clickable(interactionSource = null, indication = null, enabled = enabled, onClick = onClick)
}

/**
 * Conditionally applies a modifier if `condition` is true.
 *
 * @param condition If true, applies the given [modifier], otherwise returns the original [Modifier].
 * @param modifier The modifier function to apply if the condition is met.
 * @return The modified [Modifier] if `condition` is true, otherwise the original [Modifier].
 */
fun Modifier.applyIf(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) this.modifier() else this
}

/**
 * Applies a modifier only if `value` is not null.
 *
 * @param value The value to check for nullability.
 * @param modifier The modifier function to apply if `value` is not null.
 * @return The modified [Modifier] if `value` is not null, otherwise the original [Modifier].
 */
fun <T> Modifier.applyIfNotNull(value: T?, modifier: Modifier.(T) -> Modifier): Modifier {
    return value?.let { this.modifier(it) } ?: this
}

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