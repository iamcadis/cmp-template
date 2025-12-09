package com.core.data.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.core.data.local.LocalStorage
import com.core.data.local.impl.LocalStorageImpl
import com.core.data.local.SecureStorage
import com.core.data.local.IOSSecureStorage
import com.core.data.remote.ConnectivityObserver
import com.core.data.remote.IosConnectivityObserverObserver
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


@OptIn(ExperimentalForeignApi::class)
internal actual val platformModule = module {
    single<SecureStorage> {
        IOSSecureStorage()
    }
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
    single<ConnectivityObserver> {
        IosConnectivityObserverObserver()
    }
    single<HttpClientEngine> {
        Darwin.create {
            configureRequest {
                setAllowsCellularAccess(true)
            }
        }
    }
}