package com.android.example.kotlinproject.b.kotlinadvanced

sealed class Expr

data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()

fun eval(expr: Expr): Double = when (expr) {
    is Const -> {
        expr.number
    }
    is Sum -> {
        eval(expr.e1) + eval(expr.e2)
    }
    is NotANumber -> {
        Double.NaN
    }
}

fun main() {
    val num1 = Const(10.0)
    val num2 = Const(20.0)
    val sum = Sum(num1, num2)
    val result = eval(sum)
    println("result : $result")  //30.0

}



