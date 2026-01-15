package com.core.data.repository.api

import com.core.data.remote.dto.LoginRequestDto

interface AuthRepository {
    suspend fun login(data: LoginRequestDto) : Result<Unit>
}