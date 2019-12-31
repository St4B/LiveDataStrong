package com.quadible.livedatastrong

import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LiveDataTimerTest {

    @Test
    fun `should not post event before the expected time`() {
        testPostCall(60, 10, 0, false)
    }

    @Test
    fun `should post event after the expected time`() {
        testPostCall(10, 60, 1, false)
    }

    @Test
    fun `should not post event if timer is reset`() {
        testPostCall(10, 60, 0, true)
    }

    private fun testPostCall(millis: Long, timeout: Long, times: Int, reset: Boolean) {
        //Given
        val action: () -> Unit = spyk({ })

        runBlocking {
            withTimeoutOrNull(timeout) {
                val timer = LiveDataTimer(this)
                timer.observeForever { action() }

                //When
                timer.postDelayed(millis)

                if (reset) {
                    timer.reset()
                }
            }
        }

        //Then
        verify(exactly = times) { action() }
    }

}
