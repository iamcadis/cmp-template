package com.core.data.mapper

import com.core.data.remote.dto.LoginRequestDto
import com.core.data.remote.dto.LoginResponseDto
import com.core.domain.model.LoginRequest
import com.core.domain.model.LoginResponse

fun LoginRequest.toDto() = LoginRequestDto(email, password)

fun LoginResponseDto.toDomain() = LoginResponse(userId, accessToken, refreshToken)