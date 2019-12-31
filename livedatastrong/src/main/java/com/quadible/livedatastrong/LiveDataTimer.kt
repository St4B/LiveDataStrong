package com.quadible.livedatastrong

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

/**
 * LiveData that emits a Unit value after a given period of time. This can help us to schedule actions.
 */
class LiveDataTimer constructor(private val scope: CoroutineScope) : LiveData<Unit>() {

    private lateinit var job: Job

    /**
     * Posts an event after the specified delay. If the timer is shut down this method will not do
     * anything.
     */
    fun postDelayed(millis: Long) {
        job = scope.launch {
            delay(millis)
            withContext(Dispatchers.Main) {
                value = Unit
            }
        }
    }

    /**
     * Resets the current event that is delayed.
     */
    fun reset() {
        if (::job.isInitialized) {
            job.cancel()
        }
    }

}
