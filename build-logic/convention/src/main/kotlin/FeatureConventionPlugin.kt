import extension.addKspCompiler
import extension.getBundle
import extension.getLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("base.ksp")
                apply("base.compose")
                apply("base.library")
            }

            with(extensions) {
                configure<KotlinMultiplatformExtension> {
                    sourceSets {
                        commonMain.dependencies {
                            implementation(project(":core:common"))
                            implementation(project(":core:data"))
                            implementation(project(":core:domain"))
                            implementation(project(":core:ui"))
                            implementation(getBundle("koin"))
                            implementation(getBundle("lifecycle"))
                        }
                    }
                }
            }

            dependencies {
                addKspCompiler(getLibrary("koin-ksp-compiler"))
            }
        }
    }
}