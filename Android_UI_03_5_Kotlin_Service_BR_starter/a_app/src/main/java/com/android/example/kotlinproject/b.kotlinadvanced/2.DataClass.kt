package com.android.example.kotlinproject.b.kotlinadvanced

data class SsafyData(
    val name: String,
    val type: String,
    var num: Int,
    var own: Boolean
)

fun main(args: Array<String>) {

    val ssafy20 = Ssafy20("김싸피", "ENFP", 23)
    ssafy20.myMbTi()

    val ssafy = SsafyData("김싸피", "ENFP", 23, true)
    println(ssafy)
    if (ssafy.own)
        println("my name is  ${ssafy.name}")
    else
        println("i'm not human being")

}

class Ssafy20(var name: String) {
    private var type: String = ""
    private var age: Int = 0

    constructor(name: String, type: String, age: Int) : this(name) {
        this.type = type
        this.age = age
    }

    fun myMbTi() {
        println("my name is $name and type is $type")
    }
}