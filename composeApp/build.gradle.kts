import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import task.generateIosConfig

plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.kmp.compose)
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
        commonMain.dependencies {
            api(project(":firebase:analytics"))
            implementation(project(":core:common"))
            implementation(project(":core:data"))
            implementation(project(":core:presentation"))
            implementation(libs.backhandler)
            implementation(libs.koin.compose)
            implementation(libs.koin.viewmodel)
        }
    }

    afterEvaluate {
        generateIosConfig()
    }
}