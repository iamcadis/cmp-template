package com.firebase.analytics

object NoOpAnalyticsTracker : AnalyticsTracker {
    override fun logEvent(name: String, params: Map<String, Any?>) {}
    override fun logScreen(screenName: String) {}
}