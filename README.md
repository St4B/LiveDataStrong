# LiveDataStrong
A bunch of LiveData implementations that perform helpful operations over their values.

Current implementations are:
- LiveDataBarrier
- LiveDataTimer
- LiveDataCombineLatestFrom

### LiveDataBarrier
A LiveData that is responsible for pausing value emission, till all contained sources have posted 
values.

```kotlin
    private val source1 = MutableLiveData<String>()

    private val source2 = MutableLiveData<String>()

    private val barrier = LiveDataBarrier(2)

    override fun onStart() {
        super.onStart()        
        barrier.addSource(source1) { 
            //This observer it will be triggered only if source1 and source2 have posted a value.
            text_first_source.text = it 
        }
        barrier.addSource(source2) {
            //This observer it will be triggered only if source1 and source2 have posted a value.
            text_second_source.text = it 
        }
    }
```

### LiveDataTimer
A LiveData that is responsible for posting a Unit after a specified delay. This is helpful if we want
to performan action after a specified amount of time.

```kotlin
    //We need to define a coroutine scope in order to initiate a LiveDataTimer
    private lateinit var scope: CoroutineScope

    private val timer = LiveDataTimer(scope)

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button_post.setOnClickListener {
           timer.postDelayed(2000L) //The value will be emitted after 2000 millis
        }
        
        timer.observe(this, Observer<Unit> {
            //This observer it will be triggered after the delay has passed
        })
     }
```

### LiveDataCombineLatestFrom
A LiveData that is responsible for combining the latest values from two sources and emitting the 
result of the combination. It is similar to https://rxmarbles.com/#combineLatest

```kotlin
    private val source1 = MutableLiveData<String>()

    private val source2 = MutableLiveData<String>()

    private val latestFrom = LiveDataCombineLatestFrom(::appendData).also {
        it.addFirstSource(source1)
        it.addSecondSource(source2)
    }
    
    private fun appendData(first: String?, second: String?) = "$first & $second"
    
    override fun onStart() {
        super.onStart()
         button_post_first.setOnClickListener {
             source1.value = "a string from first source"
         }
        
         button_post_second.setOnClickListener {
             source2.value = "a string from second source"
         }
        
        latestFrom.observe(this, Observer<String> {
            //'it' should be the result of appendData(String, String) with first parameter
            //the value of first source and second parameter the value of second source.
            text_result_from.text = it
        })
    }
```

Download
--------

```groovy
dependencies {
   implementation "com.quadible:livedatastrong:1.0.0-alpha"
  
   //Needed if you want to use LiveDataTimer
   implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
   implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
}
```

## License
```
 Copyright 2019 Quadible Ltd.
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
   http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```