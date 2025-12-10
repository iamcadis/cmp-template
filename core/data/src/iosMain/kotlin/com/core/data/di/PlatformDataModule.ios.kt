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
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun getPlatformDataModule() : Module {
    return module {
        single<SecureStorage> { IosSecureStorage() }
        single<LocalStorage> {
            LocalStorageImpl(
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
        single<ConnectivityObserver> { IosConnectivityObserver() }
        single<HttpClientEngine> { Darwin.create() }
    }
}