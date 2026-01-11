package com.features.auth.screen.login

import androidx.compose.runtime.Composable
import com.core.presentation.data.Validator
import com.core.presentation.util.addValidators
import kotlinx.collections.immutable.persistentListOf

@Composable
fun loginValidators(state: LoginContract.State) = addValidators(
//    "email" to persistentListOf(
//        Validator(
//            isError = state.phone.isBlank(),
//            message = stringResource(Res.string.err_number_is_required)
//        ),
//        Validator(
//            isError = state.phone.length !in 9..12,
//            message = stringResource(Res.string.err_number_is_not_valid)
//        ),
//        Validator(
//            isError = state.errorNumberNotExist,
//            message = stringResource(Res.string.number_not_registered)
//        ),
//    )
)