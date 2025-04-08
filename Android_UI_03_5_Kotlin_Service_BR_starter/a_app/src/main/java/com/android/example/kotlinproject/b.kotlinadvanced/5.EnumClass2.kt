package com.android.example.kotlinproject.b.kotlinadvanced

enum class Human(val age: Int) {
    KIM(25), CHOI(21)
}

fun main() {
    val human: Human = Human.KIM
    println("${human.name}, ${human.age}, ${human.ordinal}")

}

