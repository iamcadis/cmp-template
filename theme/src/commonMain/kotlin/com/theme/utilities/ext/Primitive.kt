package com.theme.utilities.ext

import androidx.compose.ui.graphics.Color
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
        withStyle(style = SpanStyle(color = color)) { append("*") }
    }
}