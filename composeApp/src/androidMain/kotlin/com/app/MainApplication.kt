package com.app

import android.app.Application
import com.app.di.initKoin
import com.firebase.analytics.AndroidFirebaseAnalytics
import com.firebase.analytics.di.addAnalyticsModule
import org.koin.android.ext.koin.androidContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
            addAnalyticsModule(AndroidFirebaseAnalytics())
        }
    }
}