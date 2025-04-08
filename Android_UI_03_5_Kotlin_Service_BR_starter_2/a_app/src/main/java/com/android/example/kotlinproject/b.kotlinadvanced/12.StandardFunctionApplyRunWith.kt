package com.android.example.kotlinproject.b.kotlinadvanced

data class Student2(var name : String, var age : Int)

fun main() {
    applyTest()
    runTest()
    withTest()
}

fun applyTest() {
    var student = Student2("Park", 11)

    //호출하는 객체 자체를 리턴한다.
    var student2 = student.apply {
        age = 15 //this 생략
        name = "kim" //this 생략
    }
    println(student) //Student2(name=kim, age=15)
    println(student2) //Student2(name=kim, age=15)
}

fun runTest() {
    var a = 10
    var b = 15

    //객체없이 run 단독 사용
    var result = run {
        var c = a + b
        println(c) //25
        c
    }//더하기 작업 수행 후 결과 c 반환

    //객체에 run 사용. 결과값을 리턴
    result = result.run {
        plus(5)
    }
    println(result) //30

}

fun withTest(){
    var people = People("Park", 15)

    var newAge = with(people){
        age = 20
        age
    }

    println(newAge) //20

}

data class People(var name : String, var age : Int)