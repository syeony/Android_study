package com.android.example.kotlinproject.a.kotlinbasic1

fun main() {

    val num : Array<Int> = arrayOf(1,2,3,4)
    val num2 = arrayOf(5,'a',"hi!",8)
    println(num[0])   // 1
    println(num2[1])  // a
    println(num2[2]) // hi!
//    num : Array<Int> 에 arrayOf(1,'a', "hi", 4) 라고 Int형이 아닌 값들을 넣으면 오류발생
//    반면 그냥 arrayOf() 만 한 배열에는 어떤값을 넣어도 오류가 나지 않는다.

    //0으로 초기화된 배열 5개 만들기
    val num3 = Array<Int>(5){0}
    num3.forEach {
        println(it)
    }
}


