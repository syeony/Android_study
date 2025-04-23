package com.ssafy.coroutine.sample2

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Sequentially executes doWorld followed by "Done"
fun main() = runBlocking {
    //runBlocking이므로, 현재 block 끝날때까지 대기.
    //doWorld가 coroutineScope이므로, doWorld가 끝날때까지 대기
    doWorld()
    println("Done")
}

// coroutineScope : suspended function(중단함수.)
// Concurrently executes both sections
suspend fun doWorld() = coroutineScope { // this: CoroutineScope
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}