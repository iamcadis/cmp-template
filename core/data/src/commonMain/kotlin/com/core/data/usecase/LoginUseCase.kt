package com.core.data.usecase

import com.core.data.remote.api.ApiConfig.Url
import com.core.data.remote.api.ApiService
import com.core.data.remote.dto.LoginRequestDto
import com.core.data.remote.dto.LoginResponseDto
import org.koin.core.annotation.Single

@Single
class LoginUseCase(private val api: ApiService) {
    suspend operator fun invoke(request: LoginRequestDto) = api.post<LoginResponseDto>(
        url = Url.LOGIN,
        data = request
    )
}