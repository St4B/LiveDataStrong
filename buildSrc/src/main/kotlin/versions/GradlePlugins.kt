package versions

import versions.GradlePlugins.Versions.androidGradleVersion
import versions.Libraries.Versions.kotlinVersion


object GradlePlugins {

    object Versions {

        const val androidGradleVersion = "3.5.3"

    }

    const val android = "com.android.tools.build:gradle:$androidGradleVersion"

    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

}