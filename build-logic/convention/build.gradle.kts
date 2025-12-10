plugins {
    `kotlin-dsl`
}

group = "com.convention.build_logic"

dependencies {
    compileOnly(libs.gradle.android)
    compileOnly(libs.gradle.kotlin)
    compileOnly(libs.gradle.compose)
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
        create("ksp") {
            id = "base.ksp"
            implementationClass = "KspConventionPlugin"
        }
    }
}