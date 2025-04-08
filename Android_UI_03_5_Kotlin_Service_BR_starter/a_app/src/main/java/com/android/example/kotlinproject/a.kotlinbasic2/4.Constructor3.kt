package com.android.example.kotlinproject.a.kotlinbasic2

/**
 * local value 를 선언 한 후 assign할 수 있다.
 */
fun main() {
    var mydate1 = MyDate2(2002)
    println(mydate1) //MyDate(year=2002, month=0, day=0)
    var mydate2 = MyDate2(2002, 2)
    println(mydate2) //MyDate(year=2002, month=2, day=0)
    var mydate3 = MyDate2(2002, 2, 2)
    println(mydate3) //MyDate(year=2002, month=2, day=2)
}
//local value를 선언한 후 assign할 수도 있다.
// setter로 체크해야할 경우 등 활용.(뒤에 나옴)
class MyDate2 (_year:Int) { // 주생성자
    var year:Int = _year
    var month:Int = 0
    var day:Int = 0
    constructor(_year:Int, _month:Int) : this(_year){ //부생성자
        this.month = _month
    }
    constructor(_year:Int, _month:Int, _day:Int) : this(_year, _month){ //부생성자
        this.day = _day
    }
    override fun toString(): String {
        return "MyDate(year=$year, month=$month, day=$day)"
    }

}
