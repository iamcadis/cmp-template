import com.android.build.api.dsl.LibraryExtension
import extension.addAndroidTarget
import extension.addIosTarget
import extension.configureAndroid
import extension.getPluginId
import extension.suppressDefaultWarning
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class LibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(getPluginId(alias = "kotlinMultiplatform"))
                apply(getPluginId(alias = "androidLibrary"))
            }

            with(extensions) {
                configure<LibraryExtension> {
                    configureAndroid(this)

                    defaultConfig {
                        consumerProguardFiles("consumer-rules.pro")
                    }
                }

                configure<KotlinMultiplatformExtension> {
                    addAndroidTarget()
                    addIosTarget()
                    suppressDefaultWarning()
                }
            }
        }
    }
}