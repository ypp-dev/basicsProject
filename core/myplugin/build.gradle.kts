import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    `kotlin-dsl`
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    implementation(libs.truth)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}
gradlePlugin{
    plugins {
        register("androidHilt") {
            id = "android-hilt-plugin"
            implementationClass = "com.ypp.myplugin.AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "android-room-plugin"
            implementationClass = "com.ypp.myplugin.AndroidRoomConventionPlugin"
        }
        register("featurePlugin") {
            id = "feature-plugin"
            implementationClass = "com.ypp.myplugin.AndroidFeatureConventionPlugin"
        }
    }

}