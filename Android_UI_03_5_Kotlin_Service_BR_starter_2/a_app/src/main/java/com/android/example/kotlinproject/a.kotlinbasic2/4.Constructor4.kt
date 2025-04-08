package com.android.example.kotlinproject.a.kotlinbasic2

/**
 * init block을 통한 초기화
 */
fun main() {
    var mydate1 = MyDate3(2002)
    println(mydate1) //MyDate(year=2002, month=0, day=0)
    var mydate2 = MyDate3(2002, 2)
    println(mydate2) //MyDate(year=2002, month=2, day=0)
}

class MyDate3 (year:Int) { // 주생성자
    var year:Int = 0
    var month:Int = 0
    var day:Int = 0
    init{
        println("init 호출")
        this.year = year
    }
    constructor(year:Int, month:Int) : this(year){ //부생성자
        println("부생성자 호출")
        this.month = month
    }
    override fun toString(): String {
        return "MyDate(year=$year, month=$month, day=$day)"
    }
}
