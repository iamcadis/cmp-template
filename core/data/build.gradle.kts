import extension.addKspCompiler

plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.ksp)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            androidMain.dependencies {
                implementation(libs.ktor.okhttp)
                implementation(libs.google.tink.android)
            }
            commonMain.dependencies {
                implementation(projects.core.common)
                implementation(libs.bundles.ktor)
                implementation(libs.bundles.datastore)
                implementation(libs.bundles.koin.annotations)
            }
            iosMain.dependencies {
                implementation(libs.ktor.darwin)
            }
        }
    }
}

dependencies {
    addKspCompiler(libs.koin.ksp.compiler)
}