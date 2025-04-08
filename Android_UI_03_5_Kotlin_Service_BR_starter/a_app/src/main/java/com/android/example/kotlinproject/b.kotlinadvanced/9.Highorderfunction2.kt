package com.android.example.kotlinproject.b.kotlinadvanced

//파라미터가 1개인 경우 ()밖으로 이동할 수 있다. 아래 모두 동일
fun main() {
    callFunction({println("hello")} )

    callFunction(){
        println("hello")
    }
    callFunction {
        println("hello")
    }
}

private fun callFunction( call:() -> Unit ) = call()

