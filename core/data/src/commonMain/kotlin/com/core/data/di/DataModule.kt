package com.core.data.di

import com.core.common.DefaultDispatchers
import com.core.common.DispatcherProvider
import com.core.common.Platform
import com.core.data.remote.api.ApiClient
import com.core.data.remote.api.ApiService
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataModule = module {
    includes(platformModule)
    single<DispatcherProvider> {
        DefaultDispatchers()
    }
    single<HttpClient> {
        ApiClient(engine = get(), storage = get(), isDebug = Platform.isDebug).client
    }
    single<ApiService> {
        ApiService(httpClient = get(), dispatcher = get(), connectivityObserver = get())
    }
    includes(repositoryModule)
}