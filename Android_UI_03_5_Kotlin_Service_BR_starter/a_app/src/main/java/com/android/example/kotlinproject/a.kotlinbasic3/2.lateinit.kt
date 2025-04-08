package com.android.example.kotlinproject.a.kotlinbasic3

class Person {
    lateinit var name: String // lateinte을 위한 선언
    fun test() {
        if(! ::name.isInitialized) { // 프로퍼티의 초기화 여부 판단
            println("not initialized")
        } else {
            println("initialized")
        }
    }
}
fun main() {
    val gildong = Person()
    gildong.test()
    gildong.name = "Gildong" // 이 시점에서 초기화됨(지연 초기화)
    gildong.test()
    println("name = ${gildong.name}")
}
