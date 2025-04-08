package com.android.example.kotlinproject.a.kotlinbasic1




fun main() {
    var str3 : String? = "Hello Kotlin"
    str3 = null // null을 허용하지 않음(오류 발생) println("str3: $str3")

    var tmp: String? = null
    var size = -1
    if (tmp != null) {
        size = tmp.length
    }
// or
    var temp: String? = null
    val size2 = if (temp != null) temp.length else -1



}
