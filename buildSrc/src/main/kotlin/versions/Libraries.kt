package versions

import versions.Libraries.Versions.appCompatVersion
import versions.Libraries.Versions.kotlinVersion

object Libraries {

    internal object Versions {

        const val kotlinVersion = "1.3.61"

        const val appCompatVersion = "1.1.0"

    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"

    const val coreKTX = "androidx.core:core-ktx:$appCompatVersion"

}