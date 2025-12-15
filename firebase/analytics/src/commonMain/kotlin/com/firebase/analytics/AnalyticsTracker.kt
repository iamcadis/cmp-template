package com.firebase.analytics

interface AnalyticsTracker {
    fun logEvent(name: String, params: Map<String, Any?>)
    fun logScreen(screenName: String)
    fun setEnabled(enabled: Boolean)
}