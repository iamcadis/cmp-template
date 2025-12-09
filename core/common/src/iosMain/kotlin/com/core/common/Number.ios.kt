package com.core.common

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

actual fun Double.toCurrency(languageTag: String?): String {
    val formatter = NSNumberFormatter().apply {
        languageTag?.let {
            this.locale = NSLocale(it)
        }
        this.numberStyle = NSNumberFormatterCurrencyStyle
        this.minimumFractionDigits = 2.toULong()
        this.maximumFractionDigits = 2.toULong()
    }

    val number = NSNumber(double = rounded())
    val defaultValue = "${formatter.currencySymbol}0${formatter.decimalSeparator}00"

    return formatter.stringFromNumber(number) ?: defaultValue
}