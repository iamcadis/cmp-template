package com.utility

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

actual fun String.asDateTime(pattern: String): LocalDateTime? {
    return runCatching {
        val timeZone = TimeZone.currentSystemDefault()
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val parsedDate = formatter.parse(this) ?: return null
        Instant.fromEpochMilliseconds(parsedDate.time).toLocalDateTime(timeZone)
    }.getOrNull()
}

actual fun LocalDateTime.asString(pattern: String): String? {
    return runCatching {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val calendar = Calendar.getInstance().apply {
            set(year, monthNumber - 1, dayOfMonth, hour, minute, second)
        }
        formatter.format(calendar.time)
    }.getOrNull()
}