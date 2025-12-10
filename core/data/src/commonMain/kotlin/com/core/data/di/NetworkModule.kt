package com.core.data.di

import com.core.common.DefaultDispatchers
import com.core.common.DispatcherProvider
import com.core.common.Platform
import com.core.data.local.SecureStorage
import com.core.data.remote.ConnectivityObserver
import com.core.data.remote.api.ApiClient
import com.core.data.remote.api.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {
    @Single
    fun provideDispatcher() : DispatcherProvider = DefaultDispatchers()

    @Single
    fun provideHttpClient(
        engine: HttpClientEngine,
        storage: SecureStorage
    ) : HttpClient = ApiClient(
        engine = engine,
        storage = storage,
        isDebug = Platform.isDebug
    ).client

    @Single
    fun provideApiService(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider,
        connectivityObserver: ConnectivityObserver
    ) : ApiService = ApiService(
        httpClient = httpClient,
        dispatcher = dispatcherProvider,
        connectivityObserver = connectivityObserver
    )
}