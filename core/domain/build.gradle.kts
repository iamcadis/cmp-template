plugins {
    alias(libs.plugins.convention.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.core.domain"
}