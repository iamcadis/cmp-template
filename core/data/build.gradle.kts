import extension.addKspCompiler

plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.ksp)
}

android {
    namespace = "com.core.data"
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
                implementation(projects.core.domain)
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