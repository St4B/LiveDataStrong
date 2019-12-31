import versions.*
import java.util.Date
import java.util.Properties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
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
    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)
    implementation(Libraries.lifecycle)
    testImplementation(Testing.jUnit)
    testImplementation(Testing.robolectric)
    testImplementation(Testing.kluent)
    testImplementation(Testing.mockk)
    androidTestImplementation(Testing.espressoCore)
}

tasks {
    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
        moduleName = rootProject.name
    }
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
    dependsOn(tasks.dokka)
}

val publishedGroupId = "com.quadible"
val libraryName = "livedatastrong"
val artifact = "livedatastrong"

val libraryDescription = "A bunch of LiveData that perform helpful operations over their values"

val siteUrl = "https://github.com/St4B/LiveDataStrong"
val gitUrl = "https://github.com/St4B/LiveDataStrong.git"
val issueUrl = "https://github.com/St4B/LiveDataStrong/issues"
val readmeUrl = "https://github.com/St4B/LiveDataStrong/blob/master/README.md"

val libraryVersion = "1.0.0-alpha"

val developerId = "St4B"
val developerName = "Vaios Tsitsonis"

val licenseName = "The Apache Software License, Version 2.0"
val licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
val allLicenses = "Apache-2.0"

publishing {
    publications {
        create<MavenPublication>("LiveDataStrong") {
            groupId = publishedGroupId
            artifactId = artifact
            version = libraryVersion
            artifact(dokkaJar)

            val sourcesJar by tasks.creating(Jar::class) {
                archiveClassifier.set("sources")
                //android.sourceSets["main"]
                from(sourceSets)
            }

            artifact(sourcesJar)
            artifact("$buildDir/outputs/aar/$artifact-release.aar")

            pom.withXml {
                asNode().apply {
                    appendNode("description", libraryDescription)
                    appendNode("name", rootProject.name)
                    appendNode("url", siteUrl)
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", licenseName)
                        appendNode("url", licenseUrl)
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", developerId)
                        appendNode("name", developerName)
                    }
                }
            }
        }
    }
}

bintray {
    val propertiesFile = file(project.rootDir.path + "/local.properties")

    if (!propertiesFile.exists()) return@bintray

    val properties = Properties()
    properties.load(propertiesFile.inputStream())

    user = if (properties.containsKey("bintray.user")) properties["bintray.user"] as String else ""
    key = if (properties.containsKey("bintray.apikey")) properties["bintray.apikey"] as String else ""
    publish = true

    setPublications("LiveDataStrong")

    pkg.apply {
        repo = "quadible"
        name = rootProject.name
        setLicenses(allLicenses)
        setLabels("LiveData", "Utilities", "Operators")
        vcsUrl = siteUrl
        websiteUrl = siteUrl
        issueTrackerUrl = issueUrl
        githubRepo = githubRepo
        githubReleaseNotesFile = readmeUrl

        version.apply {
            name = libraryVersion
            desc = libraryDescription
            released = Date().toString()
        }
    }
}