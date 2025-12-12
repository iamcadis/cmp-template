package com.core.domain.feat.auth

import org.koin.core.annotation.Single

@Single
class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest) = authRepository.login(request)
}