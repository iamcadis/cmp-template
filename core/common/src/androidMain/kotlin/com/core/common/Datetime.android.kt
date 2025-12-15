@file:OptIn(ExperimentalTime::class)

package com.core.common

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
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.ExperimentalTime
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

private val formatterCache = ConcurrentHashMap<String, ThreadLocal<SimpleDateFormat>>()

fun LocalDateTime.format(
    pattern: String,
    languageTag: String?,
    toZone: TimeZone? = null
): String? {
    val tag = (languageTag ?: Locale.getDefault().toLanguageTag())
        .replace("_", "-")

    val locale = Locale.forLanguageTag(tag)
    val zoneId = toZone?.id ?: TimeZone.currentSystemDefault().id
    val cacheKey = "$pattern|${locale.toLanguageTag()}|$zoneId"

    val formatter = formatterCache
        .getOrPut(cacheKey) {
            ThreadLocal.withInitial {
                SimpleDateFormat(pattern, locale).apply {
                    timeZone = JavaTimeZone.getTimeZone(zoneId)
                }
            }
        }.get()

    val millis = this.toInstant(toZone ?: TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    return formatter?.format(Date(millis))
}
