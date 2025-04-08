package com.android.example.kotlinproject.a.kotlinbasic3

import kotlin.properties.Delegates

class Vetoble{

    var age:Int by Delegates.vetoable(22) {
            property, oldValue, newValue ->
        println("property:${property.name}, $oldValue -> $newValue , result : ${oldValue > newValue}")
        oldValue > newValue
    }
}

fun main() {
    var v = Vetoble()
    println(v.age) //22
    v.age = 20
    println(v.age) //20
    v.age = 23
    println(v.age) //20
}
