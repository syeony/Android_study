package com.android.example.kotlinproject.c.kotlinIntensive

class NewList2 {
    private val list = ArrayList<Int>()

    fun addAll(nums: Collection<Int>) {
        list.addAll(nums)
    }

    fun getHigherThan(num: Int): List<Int> {
        val result = arrayListOf<Int>()
        list.forEach { it->
            if (it > num) {
                result.add(it)
            }
        }
        return result
    }
}

fun main() {
    val numbers2 = NewList2()
    numbers2.addAll(listOf(1,2,3,4,5,6))

    val filtered = numbers2.getHigherThan(3).toString()
    println(filtered)
}