plugins {
    alias(libs.plugins.convention.library)
}

android {
    namespace = "com.firebase.analytics"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}