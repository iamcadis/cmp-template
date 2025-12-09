package com.core.common

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.atTime
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.time.ExperimentalTime
import java.util.TimeZone as JavaTimeZone

@OptIn(ExperimentalTime::class)
actual fun LocalDateTime.asString(
    format: String,
    atZone: TimeZone,
    toZone: TimeZone,
    languageTag: String?
): String? {
    return this.toInstant(atZone)
        .toLocalDateTime(toZone)
        .format(format = format, languageTag = languageTag, toZone = toZone)
}

actual fun LocalDate.asString(format: String, languageTag: String?): String? {
    return this.atTime(hour = 1, minute = 1)
        .format(format = format, languageTag = languageTag)
}

actual fun LocalTime.asString(format: String, atZone: TimeZone, languageTag: String?): String? {
    return this.atDate(year = 2000, month = 1, day = 1)
        .asString(format = format, languageTag = languageTag, atZone = atZone)
}

private fun LocalDateTime.format(
    format: String,
    languageTag: String?,
    toZone: TimeZone? = null
): String? {
    val locale = languageTag?.let { Locale.forLanguageTag(languageTag) } ?: Locale.getDefault()
    val calendar = Calendar.getInstance().apply {
        toZone?.let {
            timeZone = JavaTimeZone.getTimeZone(it.id)
        }

        with(this@format) {
            set(Calendar.YEAR, date.year)
            set(Calendar.MONTH, date.month.number - 1)
            set(Calendar.DAY_OF_MONTH, date.day)
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
            set(Calendar.SECOND, time.second)
        }
    }

    return runCatching {
        val formatter = SimpleDateFormat(format, locale)
        toZone?.let {
            formatter.timeZone = JavaTimeZone.getTimeZone(it.id)
        }
        formatter.format(calendar.time)
    }.getOrNull()
}