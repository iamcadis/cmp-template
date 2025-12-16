import SwiftUI
import ComposeApp
import FirebaseCore
import AppTrackingTransparency

@main
struct iOSApp: App {
    @Environment(\.scenePhase) private var scenePhase
    
    private let analyticsHelper = AnalyticsHelper()
    
    init() {
        FirebaseApp.configure()
        KoinKt.doInitKoin(configuration: DIContainer.nativeConfiguration)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .onChange(of: scenePhase) { newPhase in
                    if newPhase == .active {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                            ATTrackingManager.requestTrackingAuthorization { status in
                                analyticsHelper.setCollectionEnabled(enabled: status == .authorized)
                            }
                        }
                    }
                }
        }
    }
}
