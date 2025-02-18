package com.utility

import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

actual fun Double.toCurrency(isoLanguage: String): String {
    return NumberFormat
        .getCurrencyInstance(Locale.forLanguageTag(isoLanguage)).apply {
            maximumFractionDigits = 2
        }
        .format(rounded())
}

actual fun <T : Number> String.parseNumber(isoLanguage: String, style: NumberStyle): T? {
    return runCatching {
        val locale = Locale.forLanguageTag(isoLanguage)
        val number = NumberFormat.getInstance(locale).parse(this)

        @Suppress("UNCHECKED_CAST")
        when (style) {
            NumberStyle.INTEGER -> number?.toInt()
            else -> number?.toDouble()
        } as? T

    }.getOrNull()
}