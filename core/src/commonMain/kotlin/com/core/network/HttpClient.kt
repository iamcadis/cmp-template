package com.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
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

    fun get(): HttpClient {
        return HttpClient {
            expectSuccess = true

            install(ContentNegotiation) {
                json(json = jsonConfig)
            }
            install(Logging) {
                level = loggerLevel
            }
            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}