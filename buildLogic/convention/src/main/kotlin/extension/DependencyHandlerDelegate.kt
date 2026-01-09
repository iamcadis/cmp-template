package extension

import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

fun DependencyHandlerDelegate.addKspCompiler(dependencyNotation: Any) {
    add("kspCommonMainMetadata", dependencyNotation)
    add("kspAndroid", dependencyNotation)
    add("kspIosArm64", dependencyNotation)
    add("kspIosSimulatorArm64", dependencyNotation)
}

fun DependencyHandlerDelegate.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}