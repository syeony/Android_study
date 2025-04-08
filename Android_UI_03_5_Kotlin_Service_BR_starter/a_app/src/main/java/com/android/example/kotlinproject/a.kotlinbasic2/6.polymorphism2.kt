package com.android.example.kotlinproject.a.kotlinbasic2

open class Human62 {
    fun play() { println("Human62.play()") }
    open fun sing() { println("Human62.sing()") }
    open fun sing2() { println("Human62.sing2()") }
}

open class Animal62 : Human62(){
    override fun sing(){ println("Animal62.sing()") }
    final override fun sing2(){ println("Animal62.sing2()") }
}

open class Animal622 : Animal62(){
    override fun sing(){ println("Animal622.sing()") }
}

fun main() {
    var animal = Animal622()
    animal.play()  //Human62.play()
    animal.sing()  //Animal622.sing()
    animal.sing2()  //Animal62.sing2()
}