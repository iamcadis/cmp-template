package com.theme.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import com.theme.transformations.NumberVisualTransformation
import com.theme.utilities.Validator
import com.utility.NumberFormatter
import com.utility.NumberStyle
import com.utility.PlatformNumberFormat
import com.utility.parseNumber

@Composable
fun <T: Number> NumberInput(
    value: T?,
    onValueChange: (T?) -> Unit,
    modifier: Modifier = Modifier,
    numberStyle: NumberStyle = NumberStyle.DECIMAL,
    locale: Locale = Locale("en-US"),
    allowNegative: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    required: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    validators: List<Validator> = emptyList(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val formatter = rememberNumberFormatter(locale.toLanguageTag(), numberStyle, allowNegative)
    var textValue by remember { mutableStateOf("") }

    LaunchedEffect(value) {
        textValue = formatter.formatForInitial(value)
    }

    TextInput(
        value = textValue,
        modifier = modifier,
        onValueChange = {
            textValue = formatter.cleanup(it)
            textValue.parseNumber<T>(locale.toLanguageTag(), numberStyle).let(onValueChange)
        },
        enabled = enabled,
        readOnly = readOnly,
        required = required,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        validators = validators,
        visualTransformation = NumberVisualTransformation(formatter = formatter),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource
    )
}

@Composable
private fun rememberNumberFormatter(
    languageTag: String,
    numberStyle: NumberStyle,
    allowNegative: Boolean
) : NumberFormatter {
    val platformFormatter = remember(languageTag) { PlatformNumberFormat.getDefault(languageTag) }

    return remember(platformFormatter, numberStyle, allowNegative) {
        NumberFormatter(platformFormatter, numberStyle, allowNegative)
    }
}