import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.convention.app)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

kotlin {
    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += "-Xbinary=bundleId=${findProperty("app.id")}"

            export(project(":firebase:analytics"))
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
        }
        commonMain.dependencies {
            api(project(":firebase:analytics"))
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
