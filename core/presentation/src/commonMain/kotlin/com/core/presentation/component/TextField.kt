package com.core.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.core.common.extension.orDefault
import com.core.presentation.data.Validator
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.annotatedLabel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import template.core.presentation.generated.resources.Res
import template.core.presentation.generated.resources.err_field_required

@Composable
fun TextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    validators: PersistentList<Validator> = persistentListOf(),
    required: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: String? = null,
    supportingText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) {
    val msgFieldRequired = stringResource(
        Res.string.err_field_required,
        label.lowercase().replaceFirstChar { it.uppercase() }
    )

    val labelView = label.takeUnless { it.isBlank() }
        ?.let { text ->
            @Composable { Text(text = text.annotatedLabel(required, color = AppTheme.colors.error)) }
        }

    val placeholderView = placeholder?.takeUnless { it.isBlank() }
        ?.let { text ->
            @Composable { Text(text = text) }
        }

    val isFocused by interactionSource.collectIsFocusedAsState()
    val validator = remember(value, required, validators) {
        if (required && value.isBlank()) {
            Validator(isError = true, message = msgFieldRequired)
        } else {
            validators.firstOrNull(Validator::isError).orDefault(Validator.Empty)
        }
    }

    val isError = remember(value, isFocused, validator) {
        validator.isError && (isFocused || value.isNotEmpty())
    }

    val supportingTextView = remember(isError, supportingText, validator) {
        val msg = if (isError) validator.message else supportingText
        msg?.takeIf { it.isNotBlank() }?.let { nonBlank ->
            @Composable { Text(nonBlank) }
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = labelView,
        placeholder = placeholderView,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingTextView,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}