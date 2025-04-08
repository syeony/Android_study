package com.android.example.kotlinproject.a.kotlinbasic1

fun main() {
    val a = 5.toByte() //Byte
    val b = 65.toChar() //Char
    val c = 10.toShort() //Short
    val d = 10 //Int
    val e = 10L //Long

    val f = 10.0f //Float
    val g = 10.0 //Double

    val s="abc"
    val s1="abc"

    if(s==s1)
    {
        print("true")
    }else{
        print("false")
    }

    var ch = '\uAC00'
    println(ch) //가

    val str = """
        Ssafy 여러분
        모두 화이팅!
        """.trimIndent()
    println(str)

}
