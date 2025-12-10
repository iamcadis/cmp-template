plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(libs.bundles.lifecycle)
        }
    }
}

android {
    namespace = "com.core.ui"
}