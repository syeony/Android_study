package com.ssafy.b_coroutine_scope

import kotlinx.coroutines.*

fun main() = runBlocking{
    println("Start : before withTimout")
    var time = withTimeout(3000) {
                println("Start withTimout")
                delay(5000)
                "after 5 seconds"
            }
    println("Start : after withTimout")
    println("Result : $time")
}