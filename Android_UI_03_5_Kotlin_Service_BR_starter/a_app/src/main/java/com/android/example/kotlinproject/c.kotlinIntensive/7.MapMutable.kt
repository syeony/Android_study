package com.android.example.kotlinproject.c.kotlinIntensive

fun main() {
    val numbersMap = mutableMapOf<String, String>( "1" to "one", "2" to "two", "3" to "three" )
    println("numbersMap: $numbersMap")

    numbersMap.put("4", "four")
    numbersMap["5"] = "five"
    println("numbersMap: $numbersMap")

    numbersMap.remove("1")
    println("numbersMap: $numbersMap")

    numbersMap.clear()
    println("numbersMap: $numbersMap")

}

