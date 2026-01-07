package com.core.domain.model

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val userId: Int, val accessToken: String, val refreshToken: String?)