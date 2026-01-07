package com.core.domain.usecase

import com.core.domain.repository.AuthRepository
import com.core.domain.model.LoginRequest
import org.koin.core.annotation.Single

@Single
class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest) = authRepository.login(request)
}