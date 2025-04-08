package com.android.example.kotlinproject.c.kotlinIntensive

import android.os.Build
import androidx.annotation.RequiresApi

// removeIf의 경우 특정 버전의 API 필요 : N 즉 API 레벨 24 이상 필요
@RequiresApi(Build.VERSION_CODES.N)
fun main() {
    val numbers = mutableSetOf<Int>(33, 22, 11, 1, 22, 3)
    println(numbers)
    numbers.add(100)
    numbers.remove(33)
    println(numbers)
    numbers.removeIf({ it < 10 }) // 10 이하의 숫자를 삭제
    println(numbers)
}