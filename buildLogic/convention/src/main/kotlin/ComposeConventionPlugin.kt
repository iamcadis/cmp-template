import extension.getPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.ComposeExtension
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
                    val compose = getByType<ComposeExtension>().dependencies

                    sourceSets {
                        androidMain.dependencies {
                            implementation(compose.preview)
                        }
                        commonMain {
                            resources.srcDirs("src/commonMain/composeResources")
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material3)
                                implementation(compose.materialIconsExtended)
                                implementation(compose.ui)
                                implementation(compose.components.resources)
                                implementation(compose.components.uiToolingPreview)
                            }
                        }
                    }

                    compilerOptions {
                        optIn.add("androidx.compose.ui.ExperimentalComposeUiApi")
                        optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
                    }
                }
            }
        }
    }
}