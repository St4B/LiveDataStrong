import versions.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(compileSdk)

    defaultConfig {
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
    testImplementation(Testing.jUnit)
    testImplementation(Testing.robolectric)
    testImplementation(Testing.kluent)
    testImplementation(Testing.mockk)
    androidTestImplementation(Testing.espressoCore)
}
