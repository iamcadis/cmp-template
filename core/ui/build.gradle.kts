plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.lifecycle)
            implementation(libs.koin.core)
            implementation(projects.core.common)
            implementation(projects.firebase.analytics)
        }
    }
}

android {
    namespace = "com.core.ui"
}