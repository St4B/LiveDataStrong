package com.quadible.livedatastrong

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Attaches the observers to the corresponding LiveData objects whenever all of them have data to emit.
 */
class LiveDataBarrier(private val size: Int): MediatorLiveData<Nothing>() {

    private val isReady = mutableListOf<Boolean>()

    private val sources = mutableListOf<LiveData<*>>()

    private val observers = mutableListOf<Observer<*>>()

    private val internalObservers = mutableListOf<IsReadyObserver<*>>()

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
        val index = sources.size

        isReady.add(false)
        sources.add(source)
        observers.add(onChanged)

        val isReadyObserver = IsReadyObserver<S>(index)
        internalObservers.add(isReadyObserver)
        source.observeForever(isReadyObserver)
    }

    private inner class IsReadyObserver<S>(val index: Int): Observer<S> {

        override fun onChanged(t: S) {
            isReady[index] = true
            if (allHaveData()) {
                attachObservers()
            }

        }

    }

    private fun allHaveData() = isReady.size == size && isReady.find { !it } == null

    private fun attachObservers() {
        for (i in 0 until  sources.size) {
            val source = sources[i]
            val observer = observers[i]
            source.observeForever(observer as Observer<in Any>)

            val internalObserver = internalObservers[i]
            source.removeObserver(internalObserver as Observer<in Any>)
        }
        sources.clear()
        observers.clear()
        isReady.clear()
        internalObservers.clear()
    }

}
