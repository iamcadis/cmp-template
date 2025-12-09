package com.core.data.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.core.data.local.AndroidSecureStorage
import com.core.data.local.LocalStorage
import com.core.data.local.impl.LocalStorageImpl
import com.core.data.local.SecureStorage
import com.core.data.remote.AndroidConnectivityObserverObserver
import com.core.data.remote.ConnectivityObserver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

internal actual val platformModule = module {
    single<SecureStorage> {
        val context = get<Context>()
        AndroidSecureStorage(
            context = context,
            dataStore = PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile("secure.preferences_pb") }
            )
        )
    }
    single<LocalStorage> {
        val context = get<Context>()
        LocalStorageImpl(
            dataStore = PreferenceDataStoreFactory.create(
                produceFile = {
                    context.preferencesDataStoreFile("local.preferences_pb")
                }
            )
        )
    }
    single<ConnectivityObserver> {
        AndroidConnectivityObserverObserver(context = get())
    }
    single<HttpClientEngine> {
        OkHttp.create {
            config {
                retryOnConnectionFailure(true)
            }
        }
    }
}