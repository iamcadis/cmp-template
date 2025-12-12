package com.core.data.feat.auth

import com.core.domain.feat.auth.LoginRequest
import com.core.domain.feat.auth.LoginResponse

fun LoginRequest.toDto() = LoginRequestDto(email, password)

fun LoginResponseDto.toDomain() = LoginResponse(userId, accessToken, refreshToken)