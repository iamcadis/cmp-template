import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    sourceSets {
        commonMain.dependencies {
            api(compose.ui)
            api(compose.runtime)
            api(compose.material3)
            api(compose.foundation)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(projects.utility)
            implementation(libs.landscapist.coil)
        }
    }
}

android {
    namespace = "com.theme"
    compileSdk = findProperty("sdk.compile").toString().toInt()

    defaultConfig {
        minSdk = findProperty("sdk.minimal").toString().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}