package com.core.domain.repository

import com.core.domain.model.LoginRequest
import com.core.domain.model.LoginResponse

interface AuthRepository {
    suspend fun login(request: LoginRequest) : LoginResponse
    suspend fun logout() : Boolean
}