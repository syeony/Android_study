package com.android.example.kotlinproject.a.kotlinbasic1

fun main() {


    val n = 2

    if ((n % 2) == 0) { // 짝수
        println("n 은 짝수 ")
        if ((n % 2) == 1) { // 홀수
            println("n 은 홀수")
        }
    }

    var number =2
    val apple = 6  // 변수가 대입 연산자에 의해 할당
    val result = apple - 2 // 표현식이 대입 연산자에 의해 결괏값 할당
   // 어떤 변수에 저장된 값으로 연산을 수행한 다음 그 결과값을 다시 변수에 할당할 땐
    // 아래와 같이 사용할 수도 있다.

    number = number + 2 // 산술 연산자와 대입 연산자를 함께 사용하는 경우
    number += 2 // 이렇게 간략하게 표현


    var num1 = 10
    var num2 = 10
    val result2 = ++num1 // num 값 증가 후 대입
    val result3 = num2++ // 먼저 num 값 대입 후 증가

    println("result2: $result2")
    println("result3: $result3")
    println("num: $num1")
    println("num1: $num2")




    var check = (5>2) && (5>1) // 2개의 항((5>2), (5>1))이 모두 참이면 true
    println("(5>2) && (5>1): $check")

    check = (4>3) || (3>5) // 2개 중 1개의 항이 참이면 true
    println("(4>3) || (3>5): $check")

    check = !(5>3) // true는 false로, false는 true로 변경
    println("!(5>3): $check")
}