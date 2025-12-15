package com.firebase.analytics.di

import com.firebase.analytics.AnalyticsTracker
import org.koin.core.KoinApplication
import org.koin.dsl.module

fun KoinApplication.addAnalyticsModule(analyticsTracker: AnalyticsTracker) {
    modules(modules = module { single<AnalyticsTracker> { analyticsTracker } })
}