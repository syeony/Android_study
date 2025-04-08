package com.android.example.kotlinproject.c.kotlinIntensive

fun main()  {

    val fruits= mutableListOf<String>("apple", "banana", "kiwi", "peach")
    fruits.remove("apple")
    fruits.add("grape")
    println("fruits: $fruits")

    fruits.addAll(listOf("melon", "cherry"))
    println("fruits: $fruits")
    fruits.removeAt(3)
    println("fruits: $fruits")

}

