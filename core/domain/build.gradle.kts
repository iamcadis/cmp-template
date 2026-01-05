import extension.addKspCompiler

plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.ksp)
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