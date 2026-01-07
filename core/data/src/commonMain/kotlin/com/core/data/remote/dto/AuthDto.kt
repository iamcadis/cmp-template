package com.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenDto(val accessToken: String, val refreshToken: String?)

@Serializable
data class LoginRequestDto(val email: String, val password: String)

@Serializable
data class LoginResponseDto(val userId: Int, val accessToken: String, val refreshToken: String?)