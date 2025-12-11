package com.app.di

import com.core.data.di.DataModule
import com.core.domain.di.DomainModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.module
import org.koin.ksp.generated.module

val coreModule: Module = module {
    includes(DataModule().module, DomainModule().module)
}

fun initKoin(configuration : KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(coreModule)
        printLogger(Level.DEBUG)
    }
}