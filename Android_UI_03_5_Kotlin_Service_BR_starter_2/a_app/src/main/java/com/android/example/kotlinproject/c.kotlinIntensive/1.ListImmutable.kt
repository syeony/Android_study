package com.android.example.kotlinproject.c.kotlinIntensive

fun main(args: Array<String>) {

//    val fruits= listOf<String>("apple", "banana", "kiwi", "peach")
 val fruits= listOf("apple", "banana", "kiwi", "peach") //-> 타입 생략 가능
    println("fruits: ${fruits}")
    println("fruits.size: ${fruits.size}")
    println("fruits.get(2): ${fruits.get(2)}")
    println("fruits[3]: ${fruits[3]}")
    println("fruits.indexOf(\"peach\"): ${fruits.indexOf("peach")}")
//    fruits.add("grapes")  // immutable이므로 추가 불가.
}

