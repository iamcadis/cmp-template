package com.app

import androidx.compose.runtime.Composable
import com.core.data.di.DataModule
import com.core.data.di.getPlatformDataModule
import com.core.domain.di.DomainModule
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.Module
import org.koin.dsl.koinConfiguration
import org.koin.ksp.generated.module

@Module(includes = [DomainModule::class, DataModule::class])
class AppModule

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinMultiplatformApplication(config = koinConfiguration {
        modules(getPlatformDataModule(), AppModule().module)
    }) {
        AppContent()
    }
}