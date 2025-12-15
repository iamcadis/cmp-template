//
// Created by Cadis on 15/12/25.
//

import Foundation
import ComposeApp
import FirebaseAnalytics

class FirebaseAnalyticsImpl : AnalyticsTracker {
    func logEvent(name: String, params: [String : Any]) {
        Analytics.logEvent(name, parameters: params)
    }
    
    func setEnabled(enabled: Bool) {
        Analytics.setAnalyticsCollectionEnabled(enabled)
    }
}
