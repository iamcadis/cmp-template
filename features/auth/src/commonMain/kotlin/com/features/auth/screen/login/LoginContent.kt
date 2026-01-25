package com.features.auth.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.presentation.component.Form
import com.core.presentation.component.Group
import com.core.presentation.component.TextInput
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.validate
import com.resources.Res
import com.resources.email
import com.resources.login
import com.resources.login_asking_for_register
import com.resources.login_instruction
import com.resources.login_instruction_caption
import com.resources.password
import com.resources.register
import org.jetbrains.compose.resources.stringResource

@Preview(showBackground = true)
@Composable
internal fun LoginContent(state: LoginState = LoginState(), onAction: (LoginAction) -> Unit = {}) {
    val validators by getLoginValidators(
        email = state.emailField.text.toString(),
        password = state.passwordField.text.toString()
    )

    Form(
        clearFocus = state.loading,
        verticalArrangement = Arrangement.spacedBy(
            space = AppTheme.dimens.default,
            alignment = Alignment.CenterVertically
        )
    ) {
        Icon(
            imageVector = Icons.Filled.Shield,
            contentDescription = "App Logo",
            tint = AppTheme.colors.primary,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(Res.string.login_instruction),
            style = AppTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(Res.string.login_instruction_caption),
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant,
            modifier = Modifier
                .offset(y = -(AppTheme.dimens.medium))
        )
        Group { modifier ->
            TextInput(
                label = stringResource(Res.string.email),
                state = state.emailField,
                validators = validators.getValue("email"),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                modifier = modifier
            )
            TextInput(
                label = stringResource(Res.string.password),
                state = state.passwordField,
                validators = validators.getValue("password"),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "Show password"
                        )
                    }
                },
                modifier = modifier
            )
        }
        Button(
            enabled = validators.validate(),
            onClick = {
                onAction(LoginAction.Login)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.small)
        ) {
            Text(text = stringResource(Res.string.login))
        }
        Row(
            modifier = Modifier
                .offset(y = -(AppTheme.dimens.small))
                .align(alignment = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.login_asking_for_register),
                style = AppTheme.typography.bodyMedium
            )
            TextButton(
                onClick = {
                    onAction(LoginAction.OpenRegister)
                },
                contentPadding = PaddingValues(
                    horizontal = AppTheme.dimens.extraSmall,
                )
            ) {
                Text(text = stringResource(Res.string.register))
            }
        }
    }
}