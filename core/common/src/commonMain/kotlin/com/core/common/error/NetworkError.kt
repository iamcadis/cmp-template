package com.core.common.error

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(val code: Int, val message: String?)

class ApiException(apiError: ApiError) : Exception("Error: ${apiError.message}")

class NoInternetException : Exception("No internet connection")