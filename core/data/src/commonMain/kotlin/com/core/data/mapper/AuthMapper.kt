package com.core.data.mapper

import com.core.data.remote.dto.LoginRequestDTO
import com.core.data.remote.dto.LoginResponseDTO
import com.core.domain.model.LoginRequest
import com.core.domain.model.LoginResponse

fun LoginRequest.toDto() = LoginRequestDTO(email, password)

fun LoginResponseDTO.toDomain() = LoginResponse(userId, accessToken, refreshToken)