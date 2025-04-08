package com.android.example.kotlinproject.a.kotlinbasic1

fun main() {

    var a: Any = 1 // Any형 a는 1로 초기화될 때 Integer형이 됨
    println("a: $a type: ${a.javaClass}") // a의 자바 기본형을 출력하면 Integer가 나옴
    a = 2L // 2L에의해 Long이 됨
    println("a: $a type: ${a.javaClass}") // a의 자바 기본형을 출력하면 long이 나옴


    checkArg("Hello") // 문자열을 인자로 넣음
    checkArg(5) // 숫자를 인자로 넣음

}

fun checkArg(x: Any) { // 인자를 Any형으로 받음
    if (x is String) {
        println("x is String: $x")
    }
    if (x is Int) {
        println("x is Int: $x")
    }
}


//
