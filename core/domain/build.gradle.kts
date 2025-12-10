import extension.addKspCompiler

plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.ksp)
}

android {
    namespace = "com.core.domain"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(libs.bundles.koin.annotations)
        }
    }
}

dependencies {
    addKspCompiler(libs.koin.ksp.compiler)
}