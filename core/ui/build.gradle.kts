plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.core.ui"
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