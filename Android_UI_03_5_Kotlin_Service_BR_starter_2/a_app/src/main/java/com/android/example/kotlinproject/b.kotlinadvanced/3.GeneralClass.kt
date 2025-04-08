package com.android.example.kotlinproject.b.kotlinadvanced

class Ssafy21(var name: String) {
    var type: String = ""
    var age: Int = 0

    constructor(name: String, type: String, age: Int) : this(name) {
        this.type = type
        this.age = age
    }

    fun myMbTi() {
        println("my name is $name and mbti is $type")
    }
}

fun main(args: Array<String>) {
    val ssafy21 = Ssafy21("name", "ENFP", 23)
    ssafy21.myMbTi()
}

