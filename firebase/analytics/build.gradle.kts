plugins {
    alias(libs.plugins.convention.library)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}