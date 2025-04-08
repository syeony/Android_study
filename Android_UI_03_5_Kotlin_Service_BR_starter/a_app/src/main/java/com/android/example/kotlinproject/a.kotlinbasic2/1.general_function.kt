package com.android.example.kotlinproject.a.kotlinbasic2

fun main() {
    println(add(10,10))
    println(add1(10,10))
    println(add2(10,10))
}

//일반함수 add,
fun add(x1:Int, x2:Int):Int {
    return x1 + x2
}

//한줄일 경우 brace 생략하고 = 으로 assign
fun add1(x1:Int, x2:Int):Int = x1 + x2

//return type 생략, 추론가능
fun add2(x1:Int, x2:Int) = x1 + x2

