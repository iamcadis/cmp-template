import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.convention.app)
    alias(libs.plugins.convention.compose)
}

kotlin {
    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += "-Xbinary=bundleId=${findProperty("app.id")}"
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity)
        }
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(projects.core.domain)
            implementation(libs.koin.compose)
            implementation(libs.lifecycle.runtime)
            implementation(libs.lifecycle.viewmodel)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.app"

    defaultConfig {
        applicationId = findProperty("app.id").toString()
    }
    buildTypes {
        val name = findProperty("app.name").toString()

        debug {
            resValue("string", "display_name", "$name Debug")
        }
        release {
            resValue("string", "display_name", name)
        }
    }
}
