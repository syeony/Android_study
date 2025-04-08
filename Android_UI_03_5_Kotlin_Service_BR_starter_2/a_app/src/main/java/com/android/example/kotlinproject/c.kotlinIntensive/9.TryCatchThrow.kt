package com.android.example.kotlinproject.c.kotlinIntensive

import java.io.*

fun readNumber(reader: BufferedReader): Int? {

    try {
        print("1 -->")
        val line = reader.readLine()
        return Integer.parseInt(line)
    } catch (e: NumberFormatException) {
        print("2 -->")
        e.printStackTrace()
    } finally {
        print("3 --> ")
        reader.close()
    }
    return null
}

fun main() {
    var reader = BufferedReader(StringReader("239"))
    print(readNumber(reader)) // 1 -->3 --> 239
    println()
    var reader2 = BufferedReader(StringReader("abc"))
    print(readNumber(reader2)) // 1 -->2 -->3 --> null
    println()

}

