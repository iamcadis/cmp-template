package com.core.data.repository

import com.core.data.mapper.toDomain
import com.core.data.mapper.toDto
import com.core.data.remote.api.ApiConfig
import com.core.data.remote.api.ApiService
import com.core.data.remote.dto.LoginResponseDTO
import com.core.data.remote.utils.NoContent
import com.core.domain.model.LoginRequest
import com.core.domain.model.LoginResponse
import com.core.domain.repository.AuthRepository
import io.ktor.http.isSuccess
import org.koin.core.annotation.Single

@Single(binds = [AuthRepository::class])
internal class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(request: LoginRequest) : LoginResponse {
        val response = apiService.post<LoginResponseDTO>(url = ApiConfig.Url.LOGIN, data = request.toDto())
        return response.toDomain()
    }

    override suspend fun logout() : Boolean {
        val response = apiService.post<NoContent>(url = ApiConfig.Url.LOGOUT, data = null)
        return response.status?.isSuccess() == true
    }
}