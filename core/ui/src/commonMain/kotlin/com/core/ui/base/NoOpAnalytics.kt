package com.core.ui.base

import com.firebase.analytics.AnalyticsTracker

object NoOpAnalytics : AnalyticsTracker {
    override fun logEvent(name: String, params: Map<String, Any?>) {}
    override fun logScreen(screenName: String) {}
}