plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.lifecycle)
            implementation(projects.core.common)
        }
    }
}

android {
    namespace = "com.core.ui"
}