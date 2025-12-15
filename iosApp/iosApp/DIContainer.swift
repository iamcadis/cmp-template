//
//  DIContainer.swift
//  iosApp
//
//  Created by Cadis on 15/12/25.
//

import ComposeApp

class DIContainer {
    static let nativeConfiguration: (Koin_coreKoinApplication) -> Void = { koinApp in
        koinApp.addAnalyticsModule(analyticsTracker: FirebaseAnalyticsImpl())
    }
}