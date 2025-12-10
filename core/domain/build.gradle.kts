plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.ksp)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
        }
    }
}

android {
    namespace = "com.core.domain"
}