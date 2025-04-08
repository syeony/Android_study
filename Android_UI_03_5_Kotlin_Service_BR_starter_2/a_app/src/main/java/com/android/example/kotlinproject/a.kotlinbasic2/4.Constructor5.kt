package com.android.example.kotlinproject.a.kotlinbasic2

/**
 * 생성자에 default value 선언
 */
fun main() {
    var mydate1 = MyDate4(2002, 2, 2)
    println(mydate1) //MyDate(year=2002, month=2, day=2)
    var mydate2 = MyDate4(month=2, day=2)
    println(mydate2) //MyDate(year=2002, month=2, day=2)
}

class MyDate4 (var year:Int=2002, var month:Int, var day:Int) { // 주생성자
    override fun toString(): String {
        return "MyDate(year=$year, month=$month, day=$day)"
    }
}
