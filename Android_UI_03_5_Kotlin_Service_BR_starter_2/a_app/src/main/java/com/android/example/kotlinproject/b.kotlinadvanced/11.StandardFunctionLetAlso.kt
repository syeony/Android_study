package com.android.example.kotlinproject.b.kotlinadvanced

data class Student(var name : String, var age : Int)

//let also
fun main() {
    letTest()
    alsoTest()
}

fun letTest() {
    var student = Student("Park",11)
    var result = student.let {
        println("${it.name + it.age}")
        it.age.plus(5)
    } //let 람다식 계산 결과를 리턴

    println(result)

    //let 람다식에서 계산한 age + age 를 반환하고 10을 빼서 출력
    val result2 = student.let { it.age + it.age }.minus(10)
    println("더하기 결과: $result2")
}

fun alsoTest(){
    var student = Student("Park",11)

    //호출하는 객체 자체를 리턴한다.
    var student2 = student.also {
        it.age = 15
        it.name = "kim"
    }

    println(student) //Student(name=kim, age=15)
    println(student2) //Student(name=kim, age=15)
}

