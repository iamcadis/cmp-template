package com.core.data.feat.auth

import com.core.domain.model.LoginRequest
import com.core.domain.model.LoginResponse

fun LoginRequest.toDto() = LoginRequestDto(email, password)

fun LoginResponseDto.toDomain() = LoginResponse(userId, accessToken, refreshToken)