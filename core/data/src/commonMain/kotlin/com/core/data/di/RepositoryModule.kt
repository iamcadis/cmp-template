package com.core.data.di

import com.core.data.repository.AuthRepositoryImpl
import com.core.domain.repository.AuthRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(apiService = get()) }
}