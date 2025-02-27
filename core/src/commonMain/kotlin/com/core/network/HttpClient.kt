package com.core.network

import com.core.local.UserPreferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.AuthConfig
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.PlatformUtils
import kotlinx.serialization.json.Json

internal object HttpClient {

    private val jsonConfig = Json {
        isLenient = true
        explicitNulls = false
        encodeDefaults = true
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
        allowSpecialFloatingPointValues = true
    }

    private val loggerLevel = when(PlatformUtils.IS_DEVELOPMENT_MODE) {
        true -> LogLevel.BODY
        false -> LogLevel.NONE
    }

    fun get(prefs: UserPreferences): HttpClient {
        return HttpClient {
            expectSuccess = true

            install(ContentNegotiation) {
                json(json = jsonConfig)
            }
            install(Auth) {
                bearerAuth(prefs)
            }
            install(Logging) {
                level = loggerLevel
            }
            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    when(exception) {
                        is ResponseException -> throw ApiException(exception.response.body<Error>())
                        else -> throw exception
                    }
                }
            }
        }
    }

    private fun AuthConfig.bearerAuth(prefs: UserPreferences) {
        bearer {
            loadTokens {
                BearerTokens(prefs.accessToken, prefs.refreshToken)
            }
            refreshTokens {
                val response = client.submitForm(
                    url = "token",
                    formParameters = Parameters.build {
                        append("refresh_token", prefs.refreshToken.orEmpty())
                    }
                ) { markAsRefreshTokenRequest() }.body<AuthResponse>()

                prefs.saveToken(response)
                BearerTokens(response.accessToken, response.refreshToken)
            }
        }
    }
}