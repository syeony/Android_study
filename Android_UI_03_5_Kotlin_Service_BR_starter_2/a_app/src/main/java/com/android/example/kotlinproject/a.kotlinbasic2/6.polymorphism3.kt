package com.android.example.kotlinproject.a.kotlinbasic2

open class Customer{ //default 생성자 선언하지 않음. Customer() 불가능.
    var name:String = ""
    var age:Int = 0
    var addr:String = ""
    constructor(name:String, age:Int, addr:String){ // 부생성자
        this.name = name
        this.addr = addr
        this.age = age
    }
    override fun toString(): String {
        return "Customer(name='$name', age=$age, addr='$addr')"
    }
}

class MainCustomer(name:String, age:Int, addr:String, var hobby:String) : Customer(name, age, addr){
    override fun toString(): String {
        return "MainCustomer(hobby='$hobby') ${super.toString()}"
    }
}

fun main() {
    var cust1 = Customer("홍길동", 20, "서울")
    println(cust1)
    println(cust1.javaClass.name)
    var cust2 = MainCustomer("홍길동", 20, "서울", "개발")
    println(cust2)
    println(cust2.javaClass.name)
}