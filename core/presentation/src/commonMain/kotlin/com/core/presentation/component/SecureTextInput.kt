package com.core.presentation.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Density
import com.core.presentation.data.Validator
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.TextInputDefaults
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SecureTextInput(
    label: String,
    state: TextFieldState,
    modifier: Modifier = Modifier,
    validators: PersistentList<Validator> = persistentListOf(),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = AppTheme.typography.bodyLarge,
    placeholder: String? = null,
    supportingText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    inputTransformation: InputTransformation? = null,
    imeAction: ImeAction = ImeAction.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedIndicatorColor = AppTheme.colors.outlineVariant,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val uiState = TextInputDefaults.rememberTextFieldUiState(
        interactionSource = interactionSource,
        state = state,
        validators = validators,
        enabled = enabled,
        label = label,
        supportingText = supportingText,
        textStyle = textStyle,
        colors = colors,
        placeholder = placeholder
    )

    var obfuscationMode by remember { mutableStateOf(TextObfuscationMode.RevealLastTyped) }
    val toggleVisibility = {
        obfuscationMode = if (obfuscationMode == TextObfuscationMode.RevealLastTyped) {
            TextObfuscationMode.Visible
        } else {
            TextObfuscationMode.RevealLastTyped
        }
    }

    val isHidden = obfuscationMode == TextObfuscationMode.RevealLastTyped
    val iconVisibility = if (isHidden) Icons.Default.Visibility else Icons.Default.VisibilityOff

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicSecureTextField(
            state = state,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = uiState.mergedTextStyle,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            onKeyboardAction = onKeyboardAction,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(uiState.cursorColor),
            inputTransformation = inputTransformation,
            textObfuscationMode = obfuscationMode,
            decorator = TextInputDefaults.decorator(
                state = state,
                colors = colors,
                uiState = uiState,
                enabled = enabled,
                leadingIcon = leadingIcon,
                trailingIcon = {
                    IconButton(onClick = toggleVisibility) {
                        Icon(
                            imageVector = iconVisibility,
                            contentDescription = if (isHidden) "Show password" else "Hide password"
                        )
                    }
                },
            ),
            scrollState = scrollState
        )
    }
}