package com.core.data.feat.auth

import com.core.data.remote.api.ApiConfig
import com.core.data.remote.api.ApiService
import com.core.data.remote.utils.NoContent
import com.core.domain.repository.AuthRepository
import com.core.domain.model.LoginRequest
import com.core.domain.model.LoginResponse
import io.ktor.http.isSuccess
import org.koin.core.annotation.Single

@Single(binds = [AuthRepository::class])
internal class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(request: LoginRequest) : LoginResponse {
        val response = apiService.post<LoginResponseDto>(url = ApiConfig.Url.LOGIN, data = request.toDto())
        return response.toDomain()
    }

    override suspend fun logout() : Boolean {
        val response = apiService.post<NoContent>(url = ApiConfig.Url.LOGOUT, data = null)
        return response.status?.isSuccess() == true
    }
}