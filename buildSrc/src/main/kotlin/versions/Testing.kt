package versions

import versions.Testing.Versions.espressoVersion
import versions.Testing.Versions.jUnitVersion
import versions.Testing.Versions.kluentVersion
import versions.Testing.Versions.mockkVersion
import versions.Testing.Versions.robolectricVersion

object Testing {

    private object Versions {

        const val jUnitVersion = "4.12"

        const val espressoVersion = "3.2.0"

        const val robolectricVersion = "4.3.1"

        const val mockkVersion = "1.9.3"

        const val kluentVersion = "1.58"

    }

    const val jUnit = "junit:junit:$jUnitVersion"

    const val espressoCore = "androidx.test.espresso:espresso-core:$espressoVersion"

    const val robolectric = "org.robolectric:robolectric:$robolectricVersion"

    const val mockk = "io.mockk:mockk:$mockkVersion"

    const val kluent = "org.amshove.kluent:kluent:$kluentVersion"

}