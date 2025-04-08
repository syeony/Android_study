package com.android.example.kotlinproject.b.kotlinadvanced

private fun highOrderFunction(a: Int, b: Int, sum: (Int, Int) -> Int): Int = sum(a, b)

fun main() {
    println(highOrderFunction(20, 30, { x, y -> x + y}))

    var result = highOrderFunction(20, 30){
            x, y -> x + y
    }

    println(result)
}


