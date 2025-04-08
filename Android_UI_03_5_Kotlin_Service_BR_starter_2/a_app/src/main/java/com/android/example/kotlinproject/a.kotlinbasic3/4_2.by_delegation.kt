package com.android.example.kotlinproject.a.kotlinbasic3

interface Base3 {
    val message: String
    fun print()
}

class BaseImpl3(val x: Int) : Base3 {
    override val message = "BaseImpl: x = $x"
    override fun print() { println(message) }
}

class Derived3(base: Base3) : Base3 by base {
    // This property is not accessed from b's implementation of `print`
    override val message = "Message of Derived"
}

fun main() {
    val b = BaseImpl3(10)
    val derived = Derived3(b)
    derived.print()      //BaseImpl: x = 10
    println(derived.message)  //Message of Derived
}