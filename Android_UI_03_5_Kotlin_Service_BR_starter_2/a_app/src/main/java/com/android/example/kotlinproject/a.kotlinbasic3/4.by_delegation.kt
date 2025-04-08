package com.android.example.kotlinproject.a.kotlinbasic3

interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

// print()를 구현해야 하지만, base를 통해서 위임 받는다.
class Derived(base: Base) : Base by base

fun main() {
    val b = BaseImpl(10)
    Derived(b).print()  // BaseImpl의 print호출
}