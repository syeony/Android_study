package com.ssafy.coroutine.sample

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    // 바깥 코루틴과 구조적인 관계를 갖는 코루틴을 실행한다. structured concurrency
    launch {
        delay(300L)
        println("World!")
    }

    println("Hello,")
}