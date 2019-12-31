import versions.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(compileSdk)

    defaultConfig {
        applicationId = "com.quadible.app"
        minSdkVersion(minSdk)
        targetSdkVersion(targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlin)
    implementation(Libraries.appCompat)
    implementation(Libraries.coreKTX)
    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)
    testImplementation(Testing.jUnit)
    androidTestImplementation(Testing.espressoCore)
}
