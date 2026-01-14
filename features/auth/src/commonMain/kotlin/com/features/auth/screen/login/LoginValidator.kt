package com.features.auth.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.core.presentation.data.Translatable
import com.core.presentation.data.Validator
import com.core.presentation.util.createValidators
import com.resources.Res
import com.resources.email
import com.resources.err_field_required
import com.resources.password
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun getLoginValidators(
    email: String,
    password: String
) = remember(email, password) {
    createValidators(
        "email" to persistentListOf(
            Validator(
                isError = email.isBlank(),
                messageRes = Res.string.err_field_required,
                formatArgs = listOf(Translatable.Resource(Res.string.email))
            )
        ),
        "password" to persistentListOf(
            Validator(
                isError = password.isBlank(),
                messageRes = Res.string.err_field_required,
                formatArgs = listOf(Translatable.Resource(Res.string.password))
            )
        )
    )
}