package com.android.example.kotlinproject.a.kotlinbasic2

/**
 * 주생성자 선언
 */

fun main() {
    var mydate = MyDate(2002, 2, 2)
    println(mydate)
}

class MyDate /*constructor*/(var year:Int, var month:Int, var day:Int) { // 주생성자
    override fun toString(): String {
        return "MyDate(year=$year, month=$month, day=$day)"
    }
}
