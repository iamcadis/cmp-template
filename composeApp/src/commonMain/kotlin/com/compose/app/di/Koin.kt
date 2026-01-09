package com.compose.app.di

import com.compose.app.AppViewModel
import com.core.data.di.DataModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.module
import org.koin.ksp.generated.module

val appModule: Module = module {
    viewModelOf(::AppViewModel)
}

fun initKoin(configuration : KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(DataModule().module, appModule)
        printLogger(Level.DEBUG)
    }
}