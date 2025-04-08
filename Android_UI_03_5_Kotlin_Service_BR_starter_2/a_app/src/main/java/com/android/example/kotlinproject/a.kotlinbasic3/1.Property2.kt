package com.android.example.kotlinproject.a.kotlinbasic3

class User2(_id: Int, _name: String, _age: Int) {
    // field는 원래 있던값, value는 새로 입력된 값
    val id: Int = _id
        get() = field  //val은 getter만 가능함.
    var name: String = _name
        get() = field
        set(value) {
            if(value.length > 10){
                println("이름이 너무 깁니다.")
            }else{
                field = value
            }
        }
    var age: Int = _age
        get() = field
        set(value) {
            println("현재값 : $field")
            if(value <0 || value > 150) {
                println("나이를 확인해 주세요.")
            }else{
                field = value
            }
        }
}

fun main() {
    val user2 = User2(1, "gildong", 30)
    user2.age = 35 // 세터 동작
    println("user1.age = ${user2.age}") // 게터 동작
}

