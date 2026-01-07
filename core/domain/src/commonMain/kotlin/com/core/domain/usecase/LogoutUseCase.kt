package com.core.domain.usecase

import com.core.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.logout()
}