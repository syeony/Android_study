package com.android.example.kotlinproject.c.kotlinIntensive

import java.io.*

fun readNumber2(reader: BufferedReader): Int? = runCatching {
    print("1 -->")
    val line = reader.readLine()
    Integer.parseInt(line)
}.onSuccess {
    print("성공")
}.onFailure { e ->
    print("실패")
}.also { //finally
    print("finally")
    reader.close()
}.getOrNull() // exception 발생하면 무시하고 null 리턴
//}.getOrDefault(-1)  // 오류발생하면 default 값 리턴.
//}.getOrElse {  } //exception발생하면 다른 로직 수행
//}.getOrThrow() // 발생한 exception throws

fun main() {
    var reader = BufferedReader(StringReader("239"))
    print(readNumber2(reader)) // 1 -->3 --> 239
    println()
    var reader2 = BufferedReader(StringReader("abc"))
    print(readNumber2(reader2)) // 1 -->2 -->3 --> null
    println()
}

