package com.quadible.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.quadible.livedatastrong.LiveDataBarrier
import kotlinx.android.synthetic.main.activity_barrier.*

class BarrierActivity : AppCompatActivity() {

    private val source1 = MutableLiveData<String>()

    private val source2 = MutableLiveData<String>()

    private val barrier = LiveDataBarrier(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barrier)
    }

    override fun onStart() {
        super.onStart()
        barrier.addSource(source1) { text_first_source.text = it }
        barrier.addSource(source2) { text_second_source.text = it }

        button_start.setOnClickListener {
            //Although the source1 will have data before the source2,
            //the results will be posted to the observers only when both sources have data.
            PostWithDelay(
                source1,
                "1st source was triggered",
                1000L
            ).start()
            PostWithDelay(
                source2,
                "2nd source was triggered",
                3000L
            ).start()
        }
    }

}

private class PostWithDelay(
    private val source: MutableLiveData<String>,
    private val value: String,
    private val delay: Long
) : Thread() {

    override fun run() {
        super.run()
        sleep(delay)
        source.postValue(value)
    }

}