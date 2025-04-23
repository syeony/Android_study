package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*


fun main() = runBlocking {
    val job = CoroutineScope(Dispatchers.Default).launch {
        delay(1000)
        println("test")
    }

    println("Start")
    println("job.isCompleted: ${job.isCompleted}")
    job.join()
    println("Done")
}


