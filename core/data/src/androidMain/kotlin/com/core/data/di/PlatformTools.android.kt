package com.core.data.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.core.data.local.AndroidSecureStorage
import com.core.data.local.LocalStorage
import com.core.data.local.SecureStorage
import com.core.data.local.impl.LocalStorageImpl
import com.core.data.remote.AndroidConnectivityObserver
import com.core.data.remote.ConnectivityObserver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual class ContextWrapper(val context: Context)

actual fun getLocalStorage(contextWrapper: ContextWrapper): LocalStorage {
    return LocalStorageImpl(
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = {
                contextWrapper.context.preferencesDataStoreFile("local.preferences_pb")
            }
        )
    )
}

actual fun getSecureStorage(contextWrapper: ContextWrapper): SecureStorage {
    return AndroidSecureStorage(
        context = contextWrapper.context,
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = {
                contextWrapper.context.preferencesDataStoreFile("secure.preferences_pb")
            }
        )
    )
}

actual fun getConnectivityObserver(contextWrapper: ContextWrapper): ConnectivityObserver {
    return AndroidConnectivityObserver(context = contextWrapper.context)
}

actual fun getHttpClientEngine(): HttpClientEngine {
    return OkHttp.create()
}