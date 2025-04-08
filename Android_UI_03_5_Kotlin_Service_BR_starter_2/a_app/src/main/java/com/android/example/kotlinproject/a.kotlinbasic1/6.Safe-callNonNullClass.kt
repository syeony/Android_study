package com.android.example.kotlinproject.a.kotlinbasic1

fun main() {
    ignoreNulls(null)
}

fun ignoreNulls(s: String?) {
    val sNotNull: String = s!!
    println(sNotNull.length)
}

//
//fun main() {
//
//    var tmp: String? = null
//    var size = -1
//    if (tmp != null) {
//        size = tmp.length
//    }
//    //or
//    var temp: String? = null
//    var size2 = if (temp != null) temp.length else -1
//
//}



//fun main(){
//    val x: String? = null
//    println(strLenSafe(x)) // null
//    println(strLenSafe("abc")) //3
//
//
//    var str: String? = "Hello Kotlin"
//    str = null
//    println("str : &str length : ${str?.length ?: -1}")  // str : &str length : -1
//
//}
//
//fun strLenSafe(s: String?):Int? {
//    println("strLenSafe : ${s?.length}")
//    return s?.length
//}

