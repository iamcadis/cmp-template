package com.utility

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toNSTimeZone
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual fun String.asDateTime(pattern: String, ): LocalDateTime? {
    return runCatching {
        val timeZone = TimeZone.currentSystemDefault()
        val formatter = NSDateFormatter().apply {
            this.timeZone = timeZone.toNSTimeZone()
            this.dateFormat = pattern
        }
        formatter.dateFromString(this)?.toKotlinInstant()?.toLocalDateTime(timeZone)
    }.getOrNull()
}

actual fun LocalDateTime.asString(pattern: String): String? {
    return runCatching {
        val formatter = NSDateFormatter().apply {
            this.locale = NSLocale.currentLocale
            this.dateFormat = pattern
        }
        val calendar = NSCalendar.currentCalendar
        val parsedDate = calendar.dateFromComponents(NSDateComponents()) ?: return null
        formatter.stringFromDate(parsedDate)
    }.getOrNull()
}