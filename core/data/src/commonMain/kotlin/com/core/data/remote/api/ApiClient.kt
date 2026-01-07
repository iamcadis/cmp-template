package com.core.data.remote.api

import com.core.common.error.ApiError
import com.core.common.error.AppException
import com.core.data.remote.dto.AuthTokenDto
import com.core.data.local.SecureStorage
import com.core.data.local.SecureStorage.Companion.getAccessToken
import com.core.data.local.SecureStorage.Companion.getRefreshToken
import com.core.data.local.SecureStorage.Companion.storeAccessToken
import com.core.data.local.SecureStorage.Companion.storeRefreshToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class ApiClient(
    private val engine: HttpClientEngine,
    private val storage: SecureStorage,
    private val isDebug: Boolean
) {

    val client: HttpClient by lazy {
        HttpClient(engine) {
            expectSuccess = true

            install(HttpTimeout) {
                connectTimeoutMillis = 10_000
                requestTimeoutMillis = 60_000
                socketTimeoutMillis = 30_000
            }

            install(ContentNegotiation) {
                json(json = Json {
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    allowStructuredMapKeys = true
                })
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        storage.getAccessToken()?.let { token ->
                            BearerTokens(accessToken = token, refreshToken = storage.getRefreshToken())
                        }
                    }

                    refreshTokens {
                        val oldRefreshToken = oldTokens?.refreshToken ?: return@refreshTokens null

                        try {
                            val response = client.post(urlString = ApiConfig.Url.REFRESH_TOKEN) {
                                markAsRefreshTokenRequest() // Crucial: prevents infinite loops
                                contentType(ContentType.Application.Json)
                                setBody(mapOf("refreshToken" to oldRefreshToken))
                            }.body<AuthTokenDto>()

                            storage.storeAccessToken(response.accessToken)
                            response.refreshToken?.let {
                                storage.storeRefreshToken(it)
                            }

                            BearerTokens(
                                accessToken = response.accessToken,
                                refreshToken = response.refreshToken
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }

                    sendWithoutRequest { request ->
                        val url = Url(urlString = ApiConfig.baseUrl)
                        request.url.host == url.host && request.url.protocol == url.protocol
                    }
                }
            }

            install(Logging) {
                level = if (isDebug) LogLevel.ALL else LogLevel.NONE
                logger = Logger.SIMPLE
            }

            defaultRequest {
                url(urlString = ApiConfig.baseUrl)
                header(key = HttpHeaders.ContentType, value = ContentType.Application.Json)
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    if (exception is ResponseException) {
                        val error = runCatching { exception.response.body<ApiError>() }.getOrNull()

                        throw if (error != null) {
                            AppException.Api(error = error)
                        } else {
                            AppException.General(throwable = exception)
                        }
                    }

                    throw AppException.General(throwable = exception)
                }
            }
        }
    }
}