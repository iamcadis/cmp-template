package extension

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun KotlinMultiplatformExtension.addAndroidTarget() {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

fun KotlinMultiplatformExtension.addIosTarget() {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

fun KotlinMultiplatformExtension.suppressDefaultWarning() {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}