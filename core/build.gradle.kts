import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
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

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.okhttp)
        }
        commonMain.dependencies {
            api(libs.ktor.serialization)
            api(libs.kotlinx.coroutines)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.lifecycle)
            implementation(libs.bundles.multiplatform.settings)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
    }
}

android {
    namespace = "com.core"
    compileSdk = findProperty("sdk.compile").toString().toInt()

    defaultConfig {
        minSdk = findProperty("sdk.minimal").toString().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}