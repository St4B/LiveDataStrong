package com.quadible.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.quadible.livedatastrong.LiveDataTimer
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class TimerActivity : AppCompatActivity(), CoroutineScope {

    companion object {

        const val KEY_BUNDLE_COUNTER = "counter"

    }

    private var counter = 0

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    private val timer = LiveDataTimer(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        button_post.setOnClickListener {
            val result = Random.nextLong(500, 3000)
            Toast.makeText(this, "Value will be emitted after $result millis", Toast.LENGTH_LONG).show()
            timer.postDelayed(result)
        }

        timer.observe(this, Observer<Unit> {
            counter++
            text_sum.text = counter.toString() }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_BUNDLE_COUNTER, counter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt(KEY_BUNDLE_COUNTER)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}