package com.core.local

import com.core.network.AuthResponse

interface UserPreferences {
    val accessToken: String
    val refreshToken: String?

    fun saveToken(response: AuthResponse)
    fun clearToken()
}