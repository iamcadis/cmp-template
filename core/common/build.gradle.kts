plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.core.common"

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.kotlinx)
        }
    }
}