package com.core.common.error


import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: Int? = null,
    val message: String? = null
)

sealed class AppException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    class Api(val error: ApiError) : AppException(message = "BE error code: ${error.code ?: 0}")
    class NoInternet : AppException(message = "No internet connection")
}