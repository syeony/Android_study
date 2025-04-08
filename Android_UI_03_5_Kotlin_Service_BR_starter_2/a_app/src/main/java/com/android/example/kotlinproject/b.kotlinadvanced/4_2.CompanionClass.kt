package com.android.example.kotlinproject.b.kotlinadvanced


class Person6{
    var id: Int = 0
    var name: String = "Hong"

    companion object{ // 고정된 static 내부 클래스처럼 정의 된다.
//    companion object Obj{   // 이름을 줄 수 있다. Person6.Obj.lang == Person6.lang
        var language: String = "Korean"
        fun work(){
            println("working...")
        }
    }
}

fun main(){
    println(Person6.language)  // 인스턴스 생성 없이 기본 값 사용
    Person6.language = "English" // 기본 값 접근 가능
    println(Person6.language)    // 변경된 내용 출력
    Person6.work()             //  메서드 실행
//    println(Person6.name)  //  name은 컴페니언 객체가 아니므로 에러!
}
