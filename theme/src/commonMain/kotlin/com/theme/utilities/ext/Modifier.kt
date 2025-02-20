package com.theme.utilities.ext

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement

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