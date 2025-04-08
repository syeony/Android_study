package com.android.example.kotlinproject.c.kotlinIntensive

fun getHigherThan(num: Int, list: List<Int>) : List<Int> {
    val result = arrayListOf<Int>()
    list.forEach { it ->
        if (it > num) {
            result.add(it)
        }
    }
    return result
}

fun main() {
    val numbers: List<Int> = listOf(1, 2, 3, 4, 5, 6)
    val filtered = getHigherThan(3, numbers).toString()
    println(filtered)
}