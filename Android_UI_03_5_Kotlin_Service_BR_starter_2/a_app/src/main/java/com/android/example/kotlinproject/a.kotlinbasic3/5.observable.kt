package com.android.example.kotlinproject.a.kotlinbasic3

import kotlin.properties.Delegates

class Observable{
    var name :String by Delegates.observable("처음"){
            property, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }
}
fun main() {
    var ob = Observable()
    ob.name = "두번째"
    ob.name = "세번째"
    /* 결과
    처음 -> 두번째
    두번째 -> 세번째
     */
}



