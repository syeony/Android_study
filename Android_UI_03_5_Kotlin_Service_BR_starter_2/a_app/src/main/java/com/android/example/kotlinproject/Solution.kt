package com.android.example.kotlinproject

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


fun main() {
//    commandline에서 들어온값 읽기.
    BufferedReader(InputStreamReader(System.`in`)).use {
        while(true){
            val line = it.readLine() ?: break //읽은 문자열
            println(line)
        }
    }

}