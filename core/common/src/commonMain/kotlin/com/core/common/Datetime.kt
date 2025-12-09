package com.core.common

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Returns the current LocalDateTime in the specified time zone.
 *
 * @param timeZone The time zone to use. Defaults to the system's default time zone.
 * @return The current LocalDateTime.
 */
@OptIn(ExperimentalTime::class)
fun LocalDateTime.Companion.current(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return Clock.System.now().toLocalDateTime(timeZone)
}

/**
 * Formats this LocalDateTime as a string.
 *
 * @param format The format to use.
 * @param atZone The time zone of this LocalDateTime. Defaults to the system's default time zone.
 * @param toZone The time zone to format the string in. Defaults to `atZone`.
 * @param languageTag The language tag to use for formatting. Defaults to the system's default locale.
 * @return The formatted string, or null if an error occurs.
 */
expect fun LocalDateTime.asString(
    format: String,
    atZone: TimeZone = TimeZone.currentSystemDefault(),
    toZone: TimeZone = atZone,
    languageTag: String? = null
): String?

/**
 * Formats this LocalDate as a string.
 *
 * @param format The format to use.
 * @param languageTag The language tag to use for formatting. Defaults to the system's default locale.
 * @return The formatted string, or null if an error occurs.
 */
expect fun LocalDate.asString(format: String, languageTag: String? = null): String?

/**
 * Formats this LocalTime as a string.
 *
 * @param format The format to use.
 * @param atZone The time zone of this LocalTime. Defaults to the system's default time zone.
 * @param languageTag The language tag to use for formatting. Defaults to the system's default locale.
 * @return The formatted string, or null if an error occurs.
 */
expect fun LocalTime.asString(
    format: String,
    atZone: TimeZone = TimeZone.currentSystemDefault(),
    languageTag: String? = null
): String?
