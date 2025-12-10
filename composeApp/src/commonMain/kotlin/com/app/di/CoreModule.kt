package com.app.di

import com.core.data.di.DataModule
import com.core.data.di.getPlatformDataModule
import com.core.domain.di.DomainModule
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module

val coreModule: Module = module {
    includes(
        getPlatformDataModule(),
        DataModule().module,
        DomainModule().module
    )
}