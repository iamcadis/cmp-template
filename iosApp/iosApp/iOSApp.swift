import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoin(configuration: DIContainer.nativeConfiguration)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
