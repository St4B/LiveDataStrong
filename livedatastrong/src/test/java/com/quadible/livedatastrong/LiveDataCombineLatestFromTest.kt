package com.quadible.livedatastrong

import androidx.lifecycle.MutableLiveData
import io.mockk.spyk
import io.mockk.verify
import org.amshove.kluent.`should equal`
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LiveDataCombineLatestFromTest {

    private val source1 = MutableLiveData<Int>()

    private val source2 = MutableLiveData<Int>()

    private val function: (Int?, Int?) -> String = spyk({ first, second -> "$first, $second" })

    private val latestForm = LiveDataCombineLatestFrom(function).also {
        it.addFirstSource(source1)
        it.addSecondSource(source2)
    }

    @Test
    fun `should invoke function even if the second source does not have any value yet`() {
        //Given
        latestForm.observeForever { /* needed in order to post values */ }

        //When
        source1.value = 8

        //Then
        verify(exactly = 1) { function(8, null) }
    }

    @Test
    fun `should invoke function even if the first source does not have any value yet`() {
        //Given
        latestForm.observeForever { /* needed in order to post values */ }

        //When
        source2.value = 7

        //Then
        verify(exactly = 1) { function(null, 7) }
    }

    @Test
    fun `should invoke function if all sources have values`() {
        //Given
        val firstValue = 2
        val secondValue = 5
        latestForm.observeForever { /* needed in order to post values */ }

        //When
        source1.value = firstValue
        source2.value = secondValue

        //Then
        verify(exactly = 1) { function(firstValue, secondValue) }
        latestForm.value `should equal` function(firstValue, secondValue)
    }

    @Test
    fun `should invoke function for all the combinations of the latest values`() {
        //Given
        val value1 = 1
        val value2 = 2
        val value3 = 3
        val value4 = 4
        latestForm.observeForever { /* needed in order to post values */ }

        //When
        source1.value = value1
        source2.value = value2
        source1.value = null
        source1.value = value3
        source2.value = value4

        //Then
        verify(exactly = 1) { function(value1, null) }
        verify(exactly = 1) { function(value1, value2) }
        verify(exactly = 1) { function(null, value2) }
        verify(exactly = 1) { function(value3, value2) }
        verify(exactly = 1) { function(value3, value4) }
        verify(exactly = 5) { function(any(), any()) }
    }

}