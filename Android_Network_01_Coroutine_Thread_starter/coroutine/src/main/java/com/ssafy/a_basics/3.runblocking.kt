package com.ssafy.coroutine.sample

import kotlinx.coroutines.*


fun main() = runBlocking {
    //현재 Context(scope) 가 아니라 CoroutineScope으로 coroutine을 생성한다.
    //별도의 CoroutineScope으로 코루틴이 생성된다.
    CoroutineScope(Dispatchers.IO).launch {
        delay(300L)
        println("World!")
    }

    println("Hello,")
    delay(500L)
}

