package com.android.example.kotlinproject.a.kotlinbasic2

class Computer { // 클래스의 정의
    // 프로퍼티들(속성)
    var name: String = "myComputer"
    var part: Int = 2
    var color: String = "blue"

    // 메서드들(함수)
    fun play() = println("computer part: $part")

    fun playGame(vol: Int) = println("game vol: $vol")
}

fun main() {
    val computer = Computer() // 클래스의 생성자를 통한 객체의 생성
    computer.color = "blue" // 객체의 프로퍼티에 값 할당
    println("computer.color: ${computer.color}") // 객체의 멤버 프로퍼티 읽기
    computer.play() // 객체의 멤버 메서드의 사용
    computer.playGame(3)
}
