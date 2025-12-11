package com.core.data.di

import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

@Module
actual class ContextModule {
    @Single
    actual fun providesContextWrapper(scope: Scope) = ContextWrapper(context = scope.get())
}
