package com.ssafy.b_coroutine_scope_launch

import kotlinx.coroutines.*


fun main() = runBlocking {
    // Scope객체 할당
    val scope = CoroutineScope(Dispatchers.Default)

    println("runBlocking Scope1 : ${coroutineContext[Job]}")

    // Dispatchers.Default로 작업 진행.
     scope.launch {
        // 코루틴 block
         println("Coroutine Default Scope2 : ${coroutineContext[Job]}")
     }

     println("runBlocking Scope3 : ${coroutineContext[Job]}")

     // CoroutineContext를 Dispatchers.IO로 재정의
     scope.launch(Dispatchers.IO) {
        // 코루틴 block
        println("Coroutine IO Scope4 : ${coroutineContext[Job]}")
    }

    println("runBlocking Scope5 : ${coroutineContext[Job]}")

}


