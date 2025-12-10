package com.app

import androidx.compose.runtime.Composable
import com.app.di.coreModule
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinMultiplatformApplication(
        config = koinConfiguration { modules(coreModule) }
    ) {
        AppContent()
    }
}