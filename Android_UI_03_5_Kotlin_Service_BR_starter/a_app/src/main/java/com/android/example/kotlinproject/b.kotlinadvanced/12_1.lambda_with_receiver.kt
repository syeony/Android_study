package com.android.example.kotlinproject.b.kotlinadvanced

//public inline fun <T> T.apply(block: T.() -> Unit): T {
//    contract {
//        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
//    }
//    block()
//    return this
//}

// function literal  with receiver
fun main() {
//val add : Int.(other: Int) -> Int

    val sum : Int.(num: Int) -> Int = {
        println("sum1 : this :${this.javaClass.name}")
        println("it :$it")
        this + it
    }

    val plus5 : Int.() -> Int = {
        println("plus5 : this :${this.javaClass.name}")
        this.plus(5)
    }

    println(100.sum(2))
    println(100.plus5())

    //----------------------------------
    val stringToInt: String.() -> Int = {
        toInt()
    }

    println("123".stringToInt().javaClass.name)

}

