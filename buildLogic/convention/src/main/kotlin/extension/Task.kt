package extension

import org.gradle.api.Task
import java.io.File

internal fun Task.generateFile(name: String, outputDir: File, fileContent: String) {
    val outputFile = outputDir.resolve(name)
    outputs.file(outputFile)

    doLast {
        outputDir.mkdirs()
        outputFile.writeText(fileContent)
    }
}