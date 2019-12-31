package versions

import versions.Libraries.Versions.appCompatVersion
import versions.Libraries.Versions.coroutinesVersion
import versions.Libraries.Versions.kotlinVersion
import versions.Libraries.Versions.lifecycleVersion

object Libraries {

    internal object Versions {

        const val kotlinVersion = "1.3.61"

        const val coroutinesVersion = "1.3.3"

        const val appCompatVersion = "1.1.0"

        const val lifecycleVersion = "2.1.0"

    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"

    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

    const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"

    const val coreKTX = "androidx.core:core-ktx:$appCompatVersion"

    const val lifecycle = "androidx.lifecycle:lifecycle-livedata:$lifecycleVersion"

}