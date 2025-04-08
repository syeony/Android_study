package com.android.example.kotlinproject.b.kotlinadvanced

private fun highOrderFunction(sum: (Int, Int) -> Int, a: Int, b: Int): Int = sum(a, b)

private fun sum(a: Int, b: Int): Int = a + b

private fun sumFunction(): Int {
    return sum(40, 2)
}

fun multi():(Int, Int) -> Int = { x: Int, y: Int -> x * y }

fun main() {
    println(highOrderFunction({ x, y -> x + y }, 20, 30))
    println(highOrderFunction(::sum, 20, 30))
    println("sumFunction result = ${sumFunction()}")

    val multiply:(Int, Int) -> Int = { x: Int, y: Int -> x * y }
    println("곱셈 결과 = ${multiply(8, 8)}")
    println("곱셈 결과 = ${multiply.invoke(8, 8)}")
    println("곱셈 결과 = ${highOrderFunction(multiply, 8,8)}")
    println("곱셈 결과 = ${highOrderFunction(multi(), 8,8)}")

}

