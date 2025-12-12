package com.core.domain.feat.auth

interface AuthRepository {
    suspend fun login(request: LoginRequest) : LoginResponse
    suspend fun logout() : Boolean
}