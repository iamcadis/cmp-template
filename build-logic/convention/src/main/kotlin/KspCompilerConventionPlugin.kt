import com.google.devtools.ksp.gradle.KspExtension
import extension.getLibrary
import extension.getPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class KspCompilerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(getPluginId(alias = "ksp"))
            }

            with(extensions) {
                configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        commonMain.dependencies {
                            implementation(getLibrary("koin-annotations"))
                        }
                    }
                    sourceSets.named("commonMain").configure {
                        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
                    }
                }

                configure<KspExtension> {
                    arg("KOIN_CONFIG_CHECK","true")
                }
            }

            val kspCompiler = getLibrary("koin-ksp-compiler")

            dependencies {
                add("kspCommonMainMetadata", kspCompiler)
                add("kspAndroid", kspCompiler)
                add("kspIosX64", kspCompiler)
                add("kspIosArm64", kspCompiler)
                add("kspIosSimulatorArm64", kspCompiler)
            }

            tasks.matching {
                it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata"
            }.configureEach {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }
    }
}