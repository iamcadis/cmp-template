package com.core.common.extension

import android.util.LruCache
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone as JavaTimeZone

actual fun LocalDateTime.asString(
    format: String,
    atZone: TimeZone,
    toZone: TimeZone,
    languageTag: String?
): String? {
    return this.toInstant(atZone)
        .toLocalDateTime(toZone)
        .format(pattern = format, languageTag = languageTag, toZone = toZone)
}

actual fun LocalDate.asString(format: String, languageTag: String?): String? {
    return this.atTime(hour = 1, minute = 1)
        .format(pattern = format, languageTag = languageTag)
}

actual fun LocalTime.asString(format: String, atZone: TimeZone, languageTag: String?): String? {
    return this.atDate(year = 2000, month = 1, day = 1)
        .asString(format = format, languageTag = languageTag, atZone = atZone)
}

private val formatterCache = LruCache<String, SimpleDateFormat>(20)

private fun LocalDateTime.format(
    pattern: String,
    languageTag: String?,
    toZone: TimeZone? = null
): String? {

    val locale = if (languageTag != null) {
        Locale.forLanguageTag(languageTag.replace("_", "-"))
    } else {
        Locale.getDefault()
    }

    val zoneId = toZone?.id ?: TimeZone.currentSystemDefault().id
    val cacheKey = "$pattern|${locale.language}|$zoneId"

    synchronized(formatterCache) {
        var formatter = formatterCache.get(cacheKey)
        val millis = this.toInstant(toZone ?: TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        if (formatter == null) {
            formatter = SimpleDateFormat(pattern, locale).apply {
                timeZone = JavaTimeZone.getTimeZone(zoneId)
            }
            formatterCache.put(cacheKey, formatter)
        }

        return formatter.format(Date(millis))
    }
}
