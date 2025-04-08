package com.android.example.kotlinproject.c.kotlinIntensive


fun main() {

    fun printAll(strings: Collection<String>) {
        for (s in strings) print("$s ")
        println()
    }

    val stringList = listOf("one", "two", "one")
    printAll(stringList)

    val stringSet = setOf("one", "two", "three")
    printAll(stringSet)
}