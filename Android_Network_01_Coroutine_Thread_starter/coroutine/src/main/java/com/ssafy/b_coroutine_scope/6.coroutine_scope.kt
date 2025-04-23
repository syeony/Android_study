package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*


fun main() = runBlocking{
    launch {
        delay(200L)
        println("2. Task from runBlocking")
    }

    coroutineScope {
        launch {
            delay(500L)
            println("3. Task from nested launch")
        }
        delay(100L)
        println("1. Task from coroutine scope")
    }
    println("4. Coroutine scope is over")

}


