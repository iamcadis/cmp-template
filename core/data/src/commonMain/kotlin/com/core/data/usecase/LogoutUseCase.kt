package com.core.data.usecase

import com.core.data.remote.api.ApiConfig
import com.core.data.remote.api.ApiService
import com.core.data.remote.utils.NoContent
import io.ktor.http.isSuccess
import org.koin.core.annotation.Single

@Single
class LogoutUseCase(private val api: ApiService) {
    suspend operator fun invoke() = api.post<NoContent>(url = ApiConfig.Url.LOGOUT)
        .status?.isSuccess() == true
}