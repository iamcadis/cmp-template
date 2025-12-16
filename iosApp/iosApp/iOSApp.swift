import SwiftUI
import ComposeApp
import FirebaseCore

@main
struct iOSApp: App {
    
    init() {
        FirebaseApp.configure()
        KoinKt.doInitKoin(configuration: DIContainer.nativeConfiguration)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
