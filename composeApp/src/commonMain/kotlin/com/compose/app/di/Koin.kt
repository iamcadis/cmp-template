package com.compose.app.di

import com.core.data.di.DataModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.ksp.generated.module

fun initKoin(configuration : KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(DataModule().module)
        printLogger(Level.DEBUG)
    }
}