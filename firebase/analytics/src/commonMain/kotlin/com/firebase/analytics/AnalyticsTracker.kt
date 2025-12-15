package com.firebase.analytics

interface AnalyticsTracker {
    fun logEvent(name: String, params: Map<String, Any?>)
    fun setEnabled(enabled: Boolean)
}