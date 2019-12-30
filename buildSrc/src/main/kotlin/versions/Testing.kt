package versions

import versions.Testing.Versions.espressoVersion
import versions.Testing.Versions.jUnitVersion

object Testing {

    private object Versions {

        const val jUnitVersion = "4.12"

        const val espressoVersion = "3.2.0"


    }

    const val jUnit = "junit:junit:$jUnitVersion"

    const val espressoCore = "androidx.test.espresso:espresso-core:$espressoVersion"

}