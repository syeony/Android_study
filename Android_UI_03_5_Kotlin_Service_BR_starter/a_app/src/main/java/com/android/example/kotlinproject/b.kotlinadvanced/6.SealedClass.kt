package com.android.example.kotlinproject.b.kotlinadvanced

sealed class Color

object Red : Color()
object Green : Color()
object Blue : Color()


fun main() {
    val color: Color = Red

// 'when' expression must be exhaustive, add necessary 'Blue' branch or 'else' branch instead
//    val font = when (color) {
//        is Red -> "Noto Sans"
//        is Green -> "Open Sans"
//        // compile error!
//    }

    val font = when (color) {
        is Red -> "Noto Sans"
        is Green -> "Open Sans"
        is Blue -> "sans-serif"
        // No error!
    }

    println("결정된 font는 : $font")
}