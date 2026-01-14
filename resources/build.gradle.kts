plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.compose)
}

compose {
    resources {
        publicResClass = true
        generateResClass = always
        packageOfResClass = "com.resources"
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.compose.resources)
        }
    }
}