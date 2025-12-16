plugins {
    alias(libs.plugins.convention.library)
}

android {
    namespace = "com.firebase.analytics"
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