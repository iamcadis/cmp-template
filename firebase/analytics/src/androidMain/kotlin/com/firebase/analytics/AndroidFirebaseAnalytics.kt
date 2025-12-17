package com.firebase.analytics

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

class AndroidFirebaseAnalytics : AnalyticsTracker {
    override fun logEvent(name: String, params: Map<String, Any?>) {
        Firebase.analytics.logEvent(name) {
            params.forEach { (key, value) ->
                when (value) {
                    is String -> param(key, value)
                    is Long -> param(key, value)
                    is Double -> param(key, value)
                    is Int -> param(key, value.toLong())
                    is Boolean -> param(key, value.toString())
                }
            }
        }
    }

    override fun logScreen(screenName: String) {
        Firebase.analytics.logEvent(name = FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "${screenName}Screen.kt")
        }
    }
}