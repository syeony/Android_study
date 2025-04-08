package com.android.example.kotlinproject.c.kotlinIntensive

class NewList : ArrayList<Int>() {
    fun getHigherThan(num: Int): List<Int> {
        val result = arrayListOf<Int>()
        this.forEach { it->
            if (it > num) {
                result.add(it)
            }
        }
        return result
    }
}

fun main() {
    val numbers1 = NewList()
    numbers1.addAll(listOf(1,2,3,4,5,6))
//    numbers1.add(1)
//    numbers1.add(2)
//    numbers1.add(3)
//    numbers1.add(4)
//    numbers1.add(5)
//    numbers1.add(6)
    val filtered = numbers1.getHigherThan(3).toString()
    println(filtered)
}