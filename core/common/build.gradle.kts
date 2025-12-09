plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.kotlinx)
        }
    }
}

android {
    namespace = "com.core.common"

    buildFeatures {
        buildConfig = true
    }
}