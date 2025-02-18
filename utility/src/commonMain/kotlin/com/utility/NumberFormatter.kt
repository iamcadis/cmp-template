package com.utility

enum class NumberStyle {
    CURRENCY, DECIMAL, INTEGER
}

expect class PlatformNumberFormat(isoLanguage: String) {
    val currencySymbol: String
    val decimalSeparator: String
    val groupingSeparator: String

    companion object {
        fun getDefault(isoLanguage: String): PlatformNumberFormat
    }
}

class NumberFormatter(
    private val formatter: PlatformNumberFormat,
    private val numberStyle: NumberStyle,
    private val allowNegative: Boolean
) {

    fun cleanup(text: String?): String {
        if (text.isNullOrBlank()) return ""

        val pattern = if (allowNegative) "[^\\d-]" else "\\D"
        val hasMinusSign = text.startsWith("-")
        val textWithoutMinus = if (hasMinusSign) text.substring(1) else text

        val inputWithoutMinus = if (textWithoutMinus.length > 1 && textWithoutMinus.startsWith('0')) {
            if (textWithoutMinus[1].isDigit()) textWithoutMinus.drop(1) else textWithoutMinus
        } else textWithoutMinus

        val input = if (hasMinusSign) "-$inputWithoutMinus" else inputWithoutMinus

        if (input.matches(pattern.toRegex())) return ""
        if (input.matches("0+".toRegex())) return "0"

        val sb = StringBuilder()
        var hasDecimalSep = false
        var decimalPlaces = 0
        val intMaxLength = if (hasMinusSign) 10 else 9

        for (char in input) {
            if (hasDecimalSep && decimalPlaces >= 2) break

            if (sb.isEmpty() && char == '-') {
                sb.append(char)
                continue
            }
            if (char.isDigit()) {
                sb.append(char)
                if (hasDecimalSep) {
                    decimalPlaces++
                }
                if (numberStyle == NumberStyle.INTEGER && sb.length >= intMaxLength) {
                    break
                }
                continue
            }
            if (char == formatter.decimalSeparator.first() && !hasDecimalSep && sb.isNotEmpty()) {
                sb.append(char)
                hasDecimalSep = true
            }
        }

        return sb.toString()
    }

    fun formatForVisual(input: String?): String {
        if (input.isNullOrBlank()) return ""

        val hasMinusSign = input.startsWith("-")
        val inputWithoutMinus = if (hasMinusSign) input.substring(1) else input

        val split = inputWithoutMinus.split(formatter.decimalSeparator)
        val intPart = split[0]
            .reversed()
            .chunked(3)
            .joinToString(separator = formatter.groupingSeparator)
            .reversed()

        val fraction = split.getOrNull(1)?.take(2) // Take the first two digits of the fraction part
        val minusSign = if (hasMinusSign) "-" else ""
        val currencySymbol = if (numberStyle != NumberStyle.CURRENCY) "" else {
            if (input.isNotEmpty()) formatter.currencySymbol else ""
        }

        val realValue = if (fraction == null) intPart else {
            intPart + formatter.decimalSeparator + fraction
        }

        return minusSign + currencySymbol + realValue
    }

    fun <T: Number>formatForInitial(value: T?): String {
        val stringValue = if (value is Double) {
            if (value % 1 == 0.0) value.toInt().toString() else value.toString()
        } else {
            value.toString()
        }
        return cleanup(stringValue)
    }
}