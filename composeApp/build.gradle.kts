import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
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
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.activity.compose)
        }
        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.theme)
            implementation(projects.utility)
            implementation(libs.navigation)
            implementation(libs.bundles.koin)
        }
    }
}

android {
    namespace = "com.app.template"
    compileSdk = findProperty("sdk.compile").toString().toInt()

    defaultConfig {
        applicationId = findProperty("app.id").toString()
        minSdk = findProperty("sdk.minimal").toString().toInt()
        targetSdk = findProperty("sdk.target").toString().toInt()
        versionCode = findProperty("version.code").toString().toInt()
        versionName = findProperty("version.name").toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

