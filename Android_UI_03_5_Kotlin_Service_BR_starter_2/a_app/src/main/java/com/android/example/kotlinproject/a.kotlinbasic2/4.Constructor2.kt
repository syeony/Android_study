package com.android.example.kotlinproject.a.kotlinbasic2

/**
 * 주/부생성자 동시에 선언가능하다.
 */
fun main() {
    var mydate1 = MyDate1(2002)
    println(mydate1) //MyDate(year=2002, month=0, day=0)
    var mydate2 = MyDate1(2002, 2)
    println(mydate2) //MyDate(year=2002, month=2, day=0)
    var mydate3 = MyDate1(2002, 2, 2)
    println(mydate3) //MyDate(year=2002, month=2, day=2)
}

class MyDate1 (var year:Int) { // 주생성자
    var month:Int = 0
    var day:Int = 0
    constructor(year:Int, month:Int) : this(year){ //부생성자
        this.month = month
    }
    constructor(year:Int, month:Int, day:Int) : this(year, month){ //부생성자
        this.day = day
    }
    override fun toString(): String {
        return "MyDate(year=$year, month=$month, day=$day)"
    }
}
