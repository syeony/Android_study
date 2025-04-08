package com.android.example.kotlinproject.a.kotlinbasic3

interface Base2 {
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl2(val x: Int) : Base2 {
    override fun printMessage() { print(x) }
    override fun printMessageLine() { println(x) }
}

class Derived2(base: Base2) : Base2 by base {
    override fun printMessage() { print("abc") }
}

fun main() {
    val b = BaseImpl2(10)
    Derived2(b).printMessage()     //abc
    Derived2(b).printMessageLine() // 10
}