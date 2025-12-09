plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(libs.lifecycle.runtime)
            implementation(libs.lifecycle.viewmodel)
        }
    }
}

android {
    namespace = "com.core.ui"
}