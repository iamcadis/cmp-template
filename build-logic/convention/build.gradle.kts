plugins {
    `kotlin-dsl`
}

group = "com.convention.build_logic"

dependencies {
    compileOnly(libs.gradle.android)
    compileOnly(libs.gradle.kotlin)
    compileOnly(libs.gradle.compose)
    compileOnly(libs.gradle.ksp)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        create("application") {
            id = "base.application"
            implementationClass = "AppConventionPlugin"
        }
        create("compose") {
            id = "base.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        create("library") {
            id = "base.library"
            implementationClass = "LibraryConventionPlugin"
        }
        create("google.ksp") {
            id = "base.google.ksp"
            implementationClass = "KspCompilerConventionPlugin"
        }
    }
}