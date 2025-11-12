plugins {
    alias(gradleLibs.plugins.google.ksp)
    alias(gradleLibs.plugins.kotlin.jvm)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(AppConfiguration.jdk))
    }
}

dependencies {
    ksp(libs.material.symbols.compose.ksp)
    implementation(libs.material.symbols.compose.annotation)
    compileOnly(androidx.compose.ui)
}