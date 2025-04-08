package com.android.example.kotlinproject.c.kotlinIntensive

fun main(){

    val numbers = setOf(33, 22, 11, 1, 22, 3)
    println(numbers)
    println("numbers.size: ${numbers.size}")
    println("numbers.contains(1): ${numbers.contains(1)}")
    println("numbers.isEmpty(): ${numbers.isEmpty()}")

}