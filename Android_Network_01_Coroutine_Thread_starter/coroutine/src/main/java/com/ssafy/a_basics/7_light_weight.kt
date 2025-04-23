package com.ssafy.coroutine.sample3

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//sampleStart
fun main()  {
    runBlocking {
        repeat(100_000) { // launch a lot of coroutines
            launch {
                delay(5000L)
                print(".")
            }
        }

//        repeat(100_000) { // start a lot of Thread
//            thread {
//                Thread.sleep(1000L)
//                print(".")
//            }
//        }

    }
}

//sampleEnd

