package com.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object NoContent

@Serializable
data class ApiError(val code: Int, val message: String)

@Serializable
data class AuthResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String?,
)

class ApiException(apiError: ApiError) : Exception(
    message = "Error code: ${apiError.code} with message: ${apiError.message}"
)

class NoInternetException() : Exception(
    message = "There is no internet connection"
)