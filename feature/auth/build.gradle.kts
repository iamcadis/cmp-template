plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.bundles.test)
        }
    }
}