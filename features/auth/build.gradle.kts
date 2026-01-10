plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.feature)
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.bundles.test)
        }
    }
}