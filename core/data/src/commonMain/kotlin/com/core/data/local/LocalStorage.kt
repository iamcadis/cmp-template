package com.core.data.local

import kotlinx.coroutines.flow.Flow

interface LocalStorage {
    val userId: Flow<Int?>
    val userHasLogin: Flow<Boolean>
    suspend fun storeUserId(userId: Int)
}