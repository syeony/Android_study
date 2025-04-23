package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*

fun main() = runBlocking {
    coroutineScope {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope {
        coroutineScope {
            delay(500L)
            println("Task from nested launch")
        }
        delay (100L)
        println("Task from coroutine scope")
    }
    println("Coroutine scope is over")
}

