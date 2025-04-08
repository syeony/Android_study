package com.android.example.kotlinproject.a.kotlinbasic2

//  상속 가능한 클래스를 위해 open 사용
open class Human (var name: String="홍길동", var age: Int) { // 주생성자
    fun play() = println("name: $name")
    fun sing(vol: Int) = println("Sing age: $age")
}


//  주 생성자를 사용하는 상속
class Woman (name: String, age: Int) : Human(name, age) {
    fun singHitone() = println("Happy Song!") // 새로 추가된 메서드
}

//  부 생성자를 사용하는 상속
class Man : Human {
    val race: String
    constructor(name: String, age: Int, race: String) : super(name, age) {
        this.race = race// 새로 추가된 프로퍼티
    }
}
// 위 Man class는 아래와 동일
// class Man (name: String, age: Int, val race: String) : Human(name, age) {
// }


fun main() {
    var woman = Woman("사임당", 20)
    var man = Man("이순신", 20, "아시아")
}
