package task

import extension.generateFile
import extension.getProperty
import org.gradle.api.Project

fun Project.generateIosConfig() {
    val iosConfigDir = rootDir.resolve("iosApp/Configuration")
    val generateIosConfig = tasks.register("GenerateIosConfig") {
        generateFile(
            name = "Config.xcconfig",
            outputDir = iosConfigDir,
            fileContent = getDefaultIosConfigContent()
        )

        generateFile(
            name = "Debug.xcconfig",
            outputDir = iosConfigDir,
            fileContent = buildString {
                appendLine("#include \"Config.xcconfig\"")
                appendLine("PRODUCT_BUNDLE_IDENTIFIER=$(inherited).debug")
                appendLine("INFOPLIST_KEY_CFBundleDisplayName=$(inherited) Debug")
            }
        )

        generateFile(
            name = "Release.xcconfig",
            outputDir = iosConfigDir,
            fileContent = buildString {
                appendLine("#include \"Config.xcconfig\"")
            }
        )
    }

    tasks.matching { task ->
        val name = task.name.lowercase()
        val isIosAction = "ios" in name && listOf("link", "sync", "pack").any { it in name }
        val isKotlinCompile = name.startsWith("compile") && "kotlin" in name

        isIosAction || isKotlinCompile
    }.configureEach {
        dependsOn(generateIosConfig)
    }
}

private fun Project.getDefaultIosConfigContent() : String {
    val productName = getProperty("app.name").replace(" ", "")
    val versionCode = getProperty("version.code", "1").toInt()
    val versionName = getProperty("version.name", "1")

    return buildString {
        appendLine("TEAM_ID=${getProperty("apple.team_id")}")
        appendLine("PRODUCT_NAME=${productName}")
        appendLine("PRODUCT_BUNDLE_IDENTIFIER=${getProperty("app.id")}")
        appendLine("INFOPLIST_KEY_CFBundleDisplayName=${getProperty("app.name")}")
        appendLine("CURRENT_PROJECT_VERSION=${versionCode}")
        appendLine("MARKETING_VERSION=${versionName}")
    }
}