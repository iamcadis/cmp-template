package com.core.common

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.toNSDateComponents
import kotlinx.datetime.toNSTimeZone
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone

actual fun LocalDateTime.asString(
    format: String,
    atZone: TimeZone,
    toZone: TimeZone,
    languageTag: String?
): String? {
    val nsDateComponents = this.toNSDateComponents()
    nsDateComponents.setTimeZone(atZone.toNSTimeZone())

    return nsDateComponents.format(
        format = format,
        languageTag = languageTag,
        nsTimeZone = toZone.toNSTimeZone()
    )
}

actual fun LocalDate.asString(format: String, languageTag: String?): String? {
    return this.toNSDateComponents()
        .format(format = format, languageTag = languageTag)
}

actual fun LocalTime.asString(format: String, atZone: TimeZone, languageTag: String?): String? {
    val nsDateComponents = this.atDate(year = 2000, month = 1, day = 1).toNSDateComponents()
    nsDateComponents.setTimeZone(atZone.toNSTimeZone())

    return nsDateComponents.format(
        format = format,
        languageTag = languageTag,
        nsTimeZone = atZone.toNSTimeZone()
    )
}

private fun NSDateComponents.format(
    format: String,
    languageTag: String?,
    nsTimeZone: NSTimeZone? = null,
): String? {
    return runCatching {
        val nsDate = NSCalendar.currentCalendar.dateFromComponents(this) ?: return null
        val dateFormatter = NSDateFormatter().apply {
            dateFormat = format

            languageTag?.let {
                locale = NSLocale(it)
            }
            nsTimeZone?.let {
                timeZone = it
            }
        }

        dateFormatter.stringFromDate(nsDate)
    }.getOrNull()
}