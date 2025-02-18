package com.utility

import platform.Foundation.NSLocale
import platform.Foundation.NSNumberFormatter

actual class PlatformNumberFormat actual constructor(isoLanguage: String) {

    private val formatter = NSNumberFormatter()

    init {
        formatter.locale = NSLocale(isoLanguage)
    }

    actual val currencySymbol: String
        get() = formatter.currencySymbol

    actual val decimalSeparator: String
        get() = formatter.decimalSeparator

    actual val groupingSeparator: String
        get() = formatter.groupingSeparator

    actual companion object {
        actual fun getDefault(isoLanguage: String): PlatformNumberFormat {
            return PlatformNumberFormat(isoLanguage)
        }
    }
}