plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.navigation)
        }
    }
}