package com.core.data.local

interface SecureStorage {
    suspend fun get(key: String) : String?
    suspend fun set(key: String, value: String?) : Boolean
    suspend fun remove(key: String) : Boolean

    companion object {
        suspend fun SecureStorage.storeAccessToken(token: String) : Boolean {
            return set("access_token", token)
        }

        suspend fun SecureStorage.getAccessToken() : String? {
            return get("access_token")
        }

        suspend fun SecureStorage.storeRefreshToken(token: String?) : Boolean {
            return set("refresh_token", token)
        }

        suspend fun SecureStorage.getRefreshToken() : String? {
            return get("refresh_token")
        }

        suspend fun SecureStorage.clearToken() {
            remove("access_token")
            remove("refresh_token")
        }
    }
}