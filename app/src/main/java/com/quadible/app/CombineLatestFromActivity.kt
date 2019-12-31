package com.quadible.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.quadible.livedatastrong.LiveDataCombineLatestFrom
import kotlinx.android.synthetic.main.activity_latest_from.*

class CombineLatestFromActivity : AppCompatActivity() {

    private val source1 = MutableLiveData<String>()

    private val source2 = MutableLiveData<String>()

    private val latestFrom = LiveDataCombineLatestFrom(::appendData).also {
        it.addFirstSource(source1)
        it.addSecondSource(source2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_from)
    }

    override fun onStart() {
        super.onStart()
        button_post_first.setOnClickListener {
            source1.value = input_first_source.text.toString()
        }

        button_post_second.setOnClickListener {
            source2.value = input_second_source.text.toString()
        }

        latestFrom.observe(this, Observer<String> {
            //'it' should be the result of appendData(String, String) with first parameter
            //the value of first source and second parameter the value of second source.
            text_result_from.text = it
        })
    }

    private fun appendData(first: String?, second: String?) = "$first & $second"

}