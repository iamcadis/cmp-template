package com.utility

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSNumberFormatterNoStyle

private fun NSNumberFormatter.format(value: Double): String {
    val zeroCurrency = "${currencySymbol}0${decimalSeparator}00"
    return runCatching { stringFromNumber(NSNumber(value)) ?: zeroCurrency }
        .getOrDefault(zeroCurrency)
}

actual fun Double.toCurrency(isoLanguage: String): String {
    return NSNumberFormatter().apply {
        locale = NSLocale(isoLanguage)
        numberStyle = NSNumberFormatterCurrencyStyle
        maximumFractionDigits = 2.toULong()
    }.format(
        value = rounded()
    )
}

actual fun <T : Number> String.parseNumber(isoLanguage: String, style: NumberStyle): T? {
    return runCatching {
        val formatter = NSNumberFormatter().apply {
            locale = NSLocale(isoLanguage)
        }

        @Suppress("UNCHECKED_CAST")
        when(style) {
            NumberStyle.INTEGER -> {
                formatter.numberStyle = NSNumberFormatterNoStyle
                formatter.numberFromString(this)?.intValue as? T
            }
            else -> {
                formatter.numberStyle = NSNumberFormatterDecimalStyle
                formatter.numberFromString(this)?.doubleValue as? T
            }
        }
    }.getOrNull()
}