// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(versions.GradlePlugins.android)
        classpath(versions.GradlePlugins.kotlin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("http://repository.jetbrains.com/all")
    }
}
