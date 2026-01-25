package com.features.auth.mapper

import com.core.data.remote.dto.LoginRequestDto
import com.features.auth.screen.login.LoginState

fun LoginState.toDto() = LoginRequestDto(
    email = emailField.text.toString(),
    password = passwordField.text.toString()
)