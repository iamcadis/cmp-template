package com.core.common

import java.text.NumberFormat
import java.util.Locale

actual fun Double.toCurrency(languageTag: String?): String {
    val locale = languageTag?.let { Locale.forLanguageTag(languageTag) } ?: Locale.getDefault()
    return NumberFormat
        .getCurrencyInstance(locale).apply {
            maximumFractionDigits = 2
        }
        .format(rounded())
}