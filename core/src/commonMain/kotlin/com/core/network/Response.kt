package com.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object NoContent

@Serializable
data class Error(val code: Int, val message: String)

@Serializable
data class AuthResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String?,
)

class ApiException(error: Error) : Exception(
    message = "Error code: ${error.code} with message: ${error.message}"
)