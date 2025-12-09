package com.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokenDTO(val accessToken: String, val refreshToken: String?)

@Serializable
data class LoginRequestDTO(val email: String, val password: String)

@Serializable
data class LoginResponseDTO(val userId: Int, val accessToken: String, val refreshToken: String?)