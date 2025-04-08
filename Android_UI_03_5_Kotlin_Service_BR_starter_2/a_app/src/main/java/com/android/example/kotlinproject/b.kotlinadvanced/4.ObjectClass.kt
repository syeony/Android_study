package com.android.example.kotlinproject.b.kotlinadvanced

import android.view.View

object SsafyObject {
    var name: String = ""
    var type: String = ""
    var age: Int = 0

    fun myType() {
        println("my name is $name and type is $type age is $age")
    }
}

fun main() {
    val name = "samsung"
    val type = "ENFP"
    val age = 23

    SsafyObject.name = name
    SsafyObject.type = type
    SsafyObject.age = age
    SsafyObject.myType()
    println("${SsafyObject.name} and ${SsafyObject.type} and ${SsafyObject.age}")
}


