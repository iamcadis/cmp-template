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
        create("android-application") {
            id = "base.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        create("ksp") {
            id = "base.kmp.ksp"
            implementationClass = "KspConventionPlugin"
        }
        create("compose") {
            id = "base.kmp.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        create("library") {
            id = "base.kmp.library"
            implementationClass = "LibraryConventionPlugin"
        }
        create("feature") {
            id = "base.kmp.feature"
            implementationClass = "FeatureConventionPlugin"
        }
    }
}