package com.ypp.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("android-room-plugin")
                apply("android-hilt-plugin")
            }
            // 获取Version Catalog
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // 项目依赖
                add("implementation", project(":core:data"))
                
                // AndroidX 核心依赖
                add("implementation", libs.findLibrary("androidx.core.ktx").get())
                add("implementation", libs.findLibrary("androidx.appcompat").get())
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                
                // UI 相关依赖
                add("implementation", libs.findLibrary("coil.compose").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                add("implementation", libs.findLibrary("androidx.activity.compose").get())
                
                // Compose 相关依赖
                add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
                add("implementation", libs.findLibrary("androidx.ui").get())
                add("implementation", libs.findLibrary("androidx.ui.graphics").get())
                add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx.material3").get())
                
                // 测试依赖
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
                add("androidTestImplementation", platform(libs.findLibrary("androidx.compose.bom").get()))
                add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
                
                // Debug 依赖
                add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
                add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}
