import extension.getBundle
import extension.getLibrary
import extension.getPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


@Suppress("unused")
class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(getPluginId(alias = "compose-compiler"))
                apply(getPluginId(alias = "compose-multiplatform"))
            }

            with(extensions) {
                configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        commonMain.dependencies {
                            implementation(getBundle("compose"))
                            implementation(getBundle("material3-adaptive"))
                        }
                    }

                    compilerOptions {
                        optIn.add("androidx.compose.ui.ExperimentalComposeUiApi")
                        optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
                        optIn.add("androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi")
                    }
                }
            }

            dependencies {
                "androidRuntimeClasspath"(getLibrary("compose-uiTooling"))
            }
        }
    }
}