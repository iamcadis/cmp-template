package com.core.data.di

import com.core.data.local.LocalStorage
import com.core.data.local.SecureStorage
import com.core.data.remote.ConnectivityObserver
import io.ktor.client.engine.HttpClientEngine

expect class ContextWrapper

expect fun getLocalStorage(contextWrapper: ContextWrapper) : LocalStorage

expect fun getSecureStorage(contextWrapper: ContextWrapper) : SecureStorage

expect fun getConnectivityObserver(contextWrapper: ContextWrapper) : ConnectivityObserver

expect fun getHttpClientEngine() : HttpClientEngine