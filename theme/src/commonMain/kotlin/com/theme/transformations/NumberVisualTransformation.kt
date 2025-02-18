package com.theme.transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.utility.NumberFormatter

class NumberVisualTransformation(private val formatter: NumberFormatter) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val inputText = text.text
        val formattedNumber = formatter.formatForVisual(inputText)

        val newText = AnnotatedString(
            text = formattedNumber,
            spanStyles = text.spanStyles,
            paragraphStyles = text.paragraphStyles
        )

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return formattedNumber.length
            }
            override fun transformedToOriginal(offset: Int): Int {
                return inputText.length
            }
        }

        return TransformedText(newText, offsetMapping)
    }
}