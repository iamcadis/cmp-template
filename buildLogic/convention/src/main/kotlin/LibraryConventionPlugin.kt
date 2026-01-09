import com.android.build.api.dsl.androidLibrary
import extension.addIosTarget
import extension.getDynamicNameSpace
import extension.getPluginId
import extension.setDefaultJvmTarget
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
                apply(getPluginId(alias = "kotlin-multiplatform"))
                apply(getPluginId(alias = "kotlin-multiplatform-library"))
            }

            with(extensions) {
                configure<KotlinMultiplatformExtension> {
                    androidLibrary {
                        namespace = getDynamicNameSpace()
                        compileSdk = findProperty("android.targetSdk").toString().toInt()
                        minSdk = findProperty("android.minSdk").toString().toInt()

                        optimization {
                            val proguardFile = file("consumer-rules.pro")
                            if (proguardFile.exists()) {
                                consumerKeepRules.files.add(proguardFile)
                            }
                        }

                        withJava()

                        compilerOptions {
                            setDefaultJvmTarget()
                        }
                    }

                    addIosTarget()
                    suppressDefaultWarning()
                }
            }
        }
    }
}