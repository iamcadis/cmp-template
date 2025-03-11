package com.core.local

import com.core.network.AuthResponse
import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val accessToken: String
    val refreshToken: String?
    val userHasLogin: Flow<Boolean>

    fun setUserHasLogin(hasLogin: Boolean)
    fun saveToken(response: AuthResponse)
    fun clear()
}