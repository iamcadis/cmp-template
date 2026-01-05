plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.mokkery)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.lifecycle)
            implementation(libs.koin.core)
            implementation(projects.core.common)
            implementation(projects.firebase.analytics)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test)
        }
    }
}