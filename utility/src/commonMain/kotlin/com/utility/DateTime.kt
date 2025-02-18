package com.utility

import kotlinx.datetime.LocalDateTime

expect fun String.asDateTime(pattern: String): LocalDateTime?

expect fun LocalDateTime.asString(pattern: String): String?