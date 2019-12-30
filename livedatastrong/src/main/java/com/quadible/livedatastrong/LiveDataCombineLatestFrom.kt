package com.quadible.livedatastrong

import androidx.lifecycle.LiveData
import kotlin.properties.Delegates

class LiveDataCombineLatestFrom<F : Any?, S : Any?, R : Any>(
    private val function: (F?, S?) -> R
) : LiveData<R>() {

    private lateinit var first: LiveData<F>

    private var firstValue by Delegates.observable<F?>(null, { _, _, _ ->
        postValue()
    })

    private lateinit var second: LiveData<S>

    private var secondValue by Delegates.observable<S?>(null, { _, _, _ ->
        postValue()
    })

    fun addFirstSource(liveData: LiveData<F>) {
        first = liveData
        first.observeForever { firstValue = it }
    }

    fun addSecondSource(liveData: LiveData<S>) {
        second = liveData
        second.observeForever { secondValue = it }
    }

    private fun postValue() {
        val result = function(firstValue, secondValue)
        postValue(result)
    }
}
