plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.navigation)
        }
    }
}