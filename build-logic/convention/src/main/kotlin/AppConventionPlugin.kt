import com.android.build.api.dsl.ApplicationExtension
import extension.addAndroidTarget
import extension.addIosTarget
import extension.configureAndroid
import extension.getPluginId
import extension.getProperty
import extension.suppressDefaultWarning
import task.generateIosConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


@Suppress("unused")
class AppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(getPluginId(alias = "androidApplication"))
                apply(getPluginId(alias = "kotlinMultiplatform"))
                apply(getPluginId(alias = "composeMultiplatform"))
                apply(getPluginId(alias = "composeCompiler"))
            }

            with(extensions) {
                configure<ApplicationExtension> {
                    configureAndroid(this)

                    defaultConfig {
                        targetSdk = getProperty("android.targetSdk").toInt()
                    }
                    buildTypes {
                        debug {
                            applicationIdSuffix = ".debug"
                        }
                        release {
                            isMinifyEnabled = true
                            isShrinkResources = true
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )
                        }
                    }
                }

                configure<KotlinMultiplatformExtension> {
                    addAndroidTarget()
                    addIosTarget()
                    suppressDefaultWarning()
                }
            }

            afterEvaluate {
                generateIosConfig()
            }
        }
    }
}