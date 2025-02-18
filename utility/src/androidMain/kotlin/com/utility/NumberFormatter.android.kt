package com.utility

import java.text.DecimalFormatSymbols
import java.util.Locale

actual class PlatformNumberFormat actual constructor(isoLanguage: String) {

    private val formatter = DecimalFormatSymbols.getInstance(Locale.forLanguageTag(isoLanguage))

    actual val currencySymbol: String
        get() = formatter.currencySymbol

    actual val decimalSeparator: String
        get() = formatter.decimalSeparator.toString()

    actual val groupingSeparator: String
        get() = formatter.groupingSeparator.toString()

    actual companion object {
        actual fun getDefault(isoLanguage: String): PlatformNumberFormat {
            return PlatformNumberFormat(isoLanguage)
        }
    }
}