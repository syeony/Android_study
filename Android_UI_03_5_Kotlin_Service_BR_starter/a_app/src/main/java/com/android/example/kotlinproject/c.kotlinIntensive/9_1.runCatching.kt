package com.android.example.kotlinproject.c.kotlinIntensive

import java.io.*

fun readNumber1(reader: BufferedReader): Int? = runCatching {
    print("1 -->")
    val line = reader.readLine()
    Integer.parseInt(line)
}.onFailure { e ->
    print("2 -->")
    e.printStackTrace()
}.also { //finally
    print("3 --> ")
    reader.close()
}.getOrThrow()
//}.getOrNull()



fun main() {
    var reader = BufferedReader(StringReader("239"))
    print(readNumber1(reader)) // 1 -->3 --> 239
    println()
    var reader2 = BufferedReader(StringReader("abc"))
    print(readNumber1(reader2)) // 1 -->2 -->3 --> null
    println()
}

