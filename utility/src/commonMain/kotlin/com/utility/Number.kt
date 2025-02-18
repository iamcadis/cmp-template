package com.utility

import kotlin.math.round

fun Double.rounded(): Double = round(100.0 * this) / 100.0

expect fun Double.toCurrency(isoLanguage: String = "en-US"): String

expect fun <T: Number> String.parseNumber(isoLanguage: String, style: NumberStyle): T?