plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.app"

    androidResources {
        val locales = findProperty("android.localeSupport").toString()
        localeFilters.addAll(locales.split(","))
    }
    defaultConfig {
        applicationId = findProperty("app.id").toString()
        versionCode = findProperty("version.code").toString().toInt()
        versionName = findProperty("version.name").toString()
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