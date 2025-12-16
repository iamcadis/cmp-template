@file:Suppress("unused")

package com.firebase.analytics

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AnalyticsHelper : KoinComponent {
    private val tracker: AnalyticsTracker by inject()

    fun setCollectionEnabled(enabled: Boolean) {
        tracker.setEnabled(enabled)
    }
}