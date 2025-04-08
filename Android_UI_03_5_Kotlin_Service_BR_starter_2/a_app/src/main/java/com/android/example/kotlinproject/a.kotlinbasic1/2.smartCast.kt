package com.android.example.kotlinproject.a.kotlinbasic1

fun main() {

    var test1:Number = 6.2 // test1 은 Double형으로 스마트캐스팅됨.
    println("value : $test1 , data type: ${test1.javaClass}")
    test1 = 12 //Integer 형으로 스마트캐스트
    println("value : $test1 , data type: ${test1.javaClass}")
    test1 = 12L //Long 형으로 스마트캐스트
    println("value : $test1 , data type: ${test1.javaClass}")
    test1 += 12.0f //Float형으로 스마트캐스트
    println("value : $test1 , data type: ${test1.javaClass}")

    val number = 256
    if (number is Int) { // num이 Int형일 때
        print(number)
    } else if (number !is Int) { // number 이 Int형이 아닐 때, !(number is Int) 와 동일
        print("Not a Int")
    }
    println()

    //java  instanceof 는 typecheck한 후 다시 casting해야 하지만, kotlin은 casting까지 한번에

    val stringSequence = "문자열"
    val temp = stringSequence

    if(temp is String){
        println(temp.substring(1))
        // if(temp instanceof String) {
        //    String tmp = (String)tmp
        //    System.out.println(tmp.substring(1))
    }

}