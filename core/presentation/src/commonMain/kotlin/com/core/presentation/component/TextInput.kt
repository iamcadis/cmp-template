package com.core.presentation.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.core.common.extension.orDefault
import com.core.presentation.data.Validator
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.ShowViewIf
import com.core.presentation.util.resolveValidatorMessage
import com.resources.Res
import com.resources.default_placeholder
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun TextInput(
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
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.MultiLine(),
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedIndicatorColor = AppTheme.colors.outlineVariant,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val validator = remember(validators) {
        validators.firstOrNull(Validator::isError)
    }

    val isError = remember(state.text, isFocused, validator) {
        validator?.isError == true && (isFocused || state.text.isNotEmpty())
    }

    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled, isError, isFocused)
    }

    val supportingTextColor = textStyle.color.takeOrElse {
        colors.supportTextColor(enabled, isError, isFocused)
    }

    val cursorColor = if (isError) colors.errorCursorColor else colors.cursorColor
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    val defaultPlaceholder = stringResource(
        Res.string.default_placeholder,
        label.lowercase()
    )

    val labelView = remember(label) {
        label.takeUnless { it.isBlank() }?.let { @Composable {
            Text(text = it, style = AppTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
        } }
    }

    val supportingTextView = remember(isError, supportingText, validator) {
        if (validator == null && supportingText == null) return@remember null

        @Composable {
            val message = when {
                isError -> resolveValidatorMessage(validator!!)
                else -> supportingText
            }

            message?.let {
                Text(
                    text = message,
                    color = supportingTextColor,
                    style = AppTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = AppTheme.dimens.extraSmall)
                )
            }
        }
    }

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(
            state = state,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            inputTransformation = inputTransformation,
            textStyle = mergedTextStyle,
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            lineLimits = lineLimits,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(cursorColor),
            outputTransformation = outputTransformation,
            decorator = { innerTextField ->
                Column(
                    modifier = Modifier
                ) {
                    labelView?.invoke()
                    Row(
                        modifier = Modifier
                            .heightIn(min = 40.dp)
                            .indicatorLine(enabled, isError, interactionSource, colors),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.medium),
                    ) {
                        if (leadingIcon != null) {
                            Box(
                                modifier = Modifier.layoutId("Leading")
                                    .height(height = 40.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                leadingIcon()
                            }
                        }
                        if ((state.text.isNotEmpty() || isFocused) && prefix != null) {
                            Box(
                                Modifier.layoutId("Prefix")
                                    .heightIn(min = 24.dp)
                                    .wrapContentHeight()
                            ) {
                                prefix()
                            }
                        }
                        Box(
                            modifier = Modifier.weight(weight = 1f)
                                .padding(vertical = AppTheme.dimens.small)
                        ) {
                            ShowViewIf(visible = state.text.isEmpty()) {
                                Text(
                                    text = placeholder.orDefault(defaultPlaceholder),
                                    color = Color.Gray,
                                    style = mergedTextStyle
                                )
                            }
                            innerTextField()
                        }
                        if ((state.text.isNotEmpty() || isFocused) && suffix != null) {
                            Box(
                                Modifier.layoutId("Suffix")
                                    .heightIn(min = 24.dp)
                                    .wrapContentHeight()
                            ) {
                                suffix()
                            }
                        }
                        if (trailingIcon != null) {
                            Box(
                                modifier = Modifier.layoutId("Trailing")
                                    .height(height = 40.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                trailingIcon()
                            }
                        }
                    }
                    supportingTextView?.invoke()
                }
            },
            scrollState = scrollState
        )
    }
}

@Stable
private fun TextFieldColors.textColor(
    enabled: Boolean,
    isError: Boolean,
    focused: Boolean
) = when {
    !enabled -> disabledTextColor
    isError -> errorTextColor
    focused -> focusedTextColor
    else -> unfocusedTextColor
}

@Stable
private fun TextFieldColors.supportTextColor(
    enabled: Boolean,
    isError: Boolean,
    focused: Boolean
) = when {
    !enabled -> disabledTextColor
    isError -> errorSupportingTextColor
    focused -> focusedTextColor
    else -> unfocusedTextColor
}