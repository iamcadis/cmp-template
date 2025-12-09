plugins {
    alias(libs.plugins.convention.library)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.okhttp)
            implementation(libs.google.tink.android)
        }
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.domain)
            implementation(libs.koin.core)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.datastore)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
    }
}

android {
    namespace = "com.core.data"
}