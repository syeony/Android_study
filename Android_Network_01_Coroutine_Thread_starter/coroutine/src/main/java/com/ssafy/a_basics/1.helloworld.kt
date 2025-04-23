package com.ssafy.coroutine.sample

import kotlinx.coroutines.*


fun main() {
    CoroutineScope(Dispatchers.IO).launch {
        delay(300L)
        println("World!")
    }

    println("Hello,")
    Thread.sleep(500L)  // Main Thread 를 대기
}
