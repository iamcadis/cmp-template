package com.core.data.di

import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

@Module
expect class ContextModule() {
    @Single
    fun providesContextWrapper(scope: Scope) : ContextWrapper
}