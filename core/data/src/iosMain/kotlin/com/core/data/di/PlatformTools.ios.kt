package com.core.data.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.core.data.local.IosSecureStorage
import com.core.data.local.LocalStorage
import com.core.data.local.SecureStorage
import com.core.data.local.impl.LocalStorageImpl
import com.core.data.remote.ConnectivityObserver
import com.core.data.remote.IosConnectivityObserver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class ContextWrapper

@OptIn(ExperimentalForeignApi::class)
actual fun getLocalStorage(contextWrapper: ContextWrapper): LocalStorage {
    return LocalStorageImpl(
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                val docDir = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null
                )
                (requireNotNull(docDir).path + "/local.preferences_pb").toPath()
            }
        )
    )
}

actual fun getSecureStorage(contextWrapper: ContextWrapper): SecureStorage {
    return IosSecureStorage()
}

actual fun getConnectivityObserver(contextWrapper: ContextWrapper): ConnectivityObserver {
    return IosConnectivityObserver()
}

actual fun getHttpClientEngine(): HttpClientEngine {
    return Darwin.create()
}