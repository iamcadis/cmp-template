package com.core.domain.usecase

import com.core.domain.model.LoginRequest
import com.core.domain.model.LoginResponse
import com.core.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest) : LoginResponse {
        return authRepository.login(request)
    }
}
