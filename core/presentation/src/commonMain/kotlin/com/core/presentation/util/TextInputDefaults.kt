package com.core.presentation.util

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.core.presentation.component.CaptionText
import com.core.presentation.data.Validator
import com.core.presentation.theme.AppTheme
import com.resources.Res
import com.resources.default_placeholder
import kotlinx.collections.immutable.PersistentList
import org.jetbrains.compose.resources.stringResource

@Stable
internal data class TextFieldUiState(
    val interactionSource: InteractionSource,
    val isFocused: Boolean,
    val isError: Boolean,
    val mergedTextStyle: TextStyle,
    val cursorColor: Color,
    val placeholder: String,
    val label: (@Composable (() -> Unit))?,
    val supportingText: (@Composable (() -> Unit))?
)

@Immutable
internal object TextInputDefaults {

    @Composable
    fun rememberTextFieldUiState(
        interactionSource: InteractionSource,
        state: TextFieldState,
        validators: PersistentList<Validator>,
        enabled: Boolean,
        label: String,
        supportingText: String?,
        textStyle: TextStyle,
        colors: TextFieldColors,
        placeholder: String?
    ): TextFieldUiState {

        val isFocused by interactionSource.collectIsFocusedAsState()

        val validator = remember(validators) {
            validators.firstOrNull(Validator::isError)
        }

        val isError = remember(state.text, isFocused, validator) {
            validator?.isError == true && (isFocused || state.text.isNotEmpty())
        }

        val textColor = textStyle.color.takeOrElse {
            when {
                !enabled -> colors.disabledTextColor
                isError -> colors.errorTextColor
                isFocused -> colors.focusedTextColor
                else -> colors.unfocusedTextColor
            }
        }

        val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
        val cursorColor = if (isError) colors.errorCursorColor else colors.cursorColor

        val labelView = remember(label) {
            label.takeUnless { it.isBlank() }?.let {
                @Composable {
                    Text(
                        text = it,
                        style = AppTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        val supportingTextView = remember(isError, supportingText, validator) {
            if (isError) {
                validator?.let {
                    @Composable {
                        CaptionText(
                            value = resolveValidatorMessage(it),
                            color = colors.errorSupportingTextColor,
                            modifier = Modifier.padding(top = AppTheme.dimens.extraSmall)
                        )
                    }
                }
            } else {
                supportingText?.let {
                    @Composable {
                        CaptionText(
                            value = it,
                            color = when {
                                !enabled -> colors.disabledSupportingTextColor
                                isFocused -> colors.focusedSupportingTextColor
                                else -> colors.unfocusedSupportingTextColor
                            },
                            modifier = Modifier.padding(top = AppTheme.dimens.extraSmall)
                        )
                    }
                }
            }
        }

        val defaultPlaceholder = stringResource(
            Res.string.default_placeholder,
            label.lowercase()
        )

        return TextFieldUiState(
            interactionSource = interactionSource,
            isFocused = isFocused,
            isError = isError,
            mergedTextStyle = mergedTextStyle,
            cursorColor = cursorColor,
            placeholder = placeholder ?: defaultPlaceholder,
            label = labelView,
            supportingText = supportingTextView
        )
    }

    fun decorator(
        state: TextFieldState,
        colors: TextFieldColors,
        uiState: TextFieldUiState,
        enabled: Boolean,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        prefix: @Composable (() -> Unit)? = null,
        suffix: @Composable (() -> Unit)? = null,
    ) = TextFieldDecorator { innerTextField ->

        Column(
            modifier = Modifier
        ) {
            uiState.label?.invoke()
            Row(
                modifier = Modifier
                    .heightIn(min = 40.dp)
                    .indicatorLine(enabled, uiState.isError, uiState.interactionSource, colors),
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
                if ((state.text.isNotEmpty() || uiState.isFocused) && prefix != null) {
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
                            text = uiState.placeholder,
                            color = Color.Gray,
                            style = uiState.mergedTextStyle
                        )
                    }
                    innerTextField()
                }
                if ((state.text.isNotEmpty() || uiState.isFocused) && suffix != null) {
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

            uiState.supportingText?.invoke()
        }
    }
}