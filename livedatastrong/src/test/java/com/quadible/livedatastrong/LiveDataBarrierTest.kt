package com.quadible.livedatastrong

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LiveDataBarrierTest {

    @Test
    fun `should not post values if one live data has not value yet`() {
        //Given
        val source1 = MutableLiveData<Int>()
        source1.value = 3
        val observe1: Observer<Int> = mockk()

        val source2 = MutableLiveData<Int>()
        val observe2: Observer<Int> = mockk()

        //When
        val barrier = LiveDataBarrier(2)
        barrier.addSource(source1, observe1)
        barrier.addSource(source2, observe2)

        //Then
        verify(exactly = 0) { observe1.onChanged(any()) }
        verify(exactly = 0) { observe2.onChanged(any()) }
    }

    @Test
    fun `should post values if all live data have values`() {
        //Given
        val value1 = 3
        val source1 = MutableLiveData<Int>()
        source1.value = value1
        val observe1: Observer<Int> = mockk(relaxed = true)

        val value2 = 5
        val source2 = MutableLiveData<Int>()
        source2.value = value2
        val observe2: Observer<Int> = mockk(relaxed = true)

        //When
        val barrier = LiveDataBarrier(2)
        barrier.addSource(source1, observe1)
        barrier.addSource(source2, observe2)

        //Then
        verify(exactly = 1) { observe1.onChanged(value1) }
        verify(exactly = 1) { observe2.onChanged(value2) }
    }

}