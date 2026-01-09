import extension.getPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class KspConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(getPluginId(alias = "ksp"))
            }

            extensions.findByType(KotlinMultiplatformExtension::class.java)
                ?.sourceSets
                ?.getByName("commonMain")
                ?.kotlin
                ?.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            tasks.matching {
                it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata"
            }.configureEach {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }
    }
}