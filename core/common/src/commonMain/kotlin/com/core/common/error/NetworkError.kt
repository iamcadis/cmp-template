package com.core.common.error


import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: Int,
    val message: String?
)

sealed class AppException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    class Api(val error: ApiError) : AppException(message = "BE error code: ${error.code}")
    class General(throwable: Throwable) : AppException(cause = throwable)
    class NoInternet : AppException(message = "No internet connection")
}