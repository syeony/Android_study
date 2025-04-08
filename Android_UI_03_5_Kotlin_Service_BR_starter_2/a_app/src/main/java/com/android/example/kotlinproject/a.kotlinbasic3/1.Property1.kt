package com.android.example.kotlinproject.a.kotlinbasic3

// 주 생성자에 3개의 매개변수 정의
class User(_id: Int, _name: String, _age: Int) {
    // 프로퍼티들
    val id: Int = _id // 불변 (읽기 전용)
    var name: String = _name // 변경 가능
    var age: Int = _age // 변경 가능
}

class User1(val id:Int, var name:String, var age:Int) { // 프로퍼티들

}

fun main() {
    val user = User1(1, "gildong", 30)
// user.id = 2 // 에러! val 프로퍼티는 값 변경 불가
    user.age = 35 // 세터 동작
    println("user1.age = ${user.age}") // 게터 동작
}

