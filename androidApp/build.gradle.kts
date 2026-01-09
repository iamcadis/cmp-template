plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
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