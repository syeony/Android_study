package com.android.example.kotlinproject.a.kotlinbasic2

open class Human61 { // open :상속 가능
    fun play() {  } // 최종 method로 overriding불가
    open fun sing() {  } //open method는 overriding 가능
    open fun sing2() {  } //open method는 overriding 가능
}

open class Animal61 : Human61(){
//    override fun play(){ } //override 금지. 오류발생
    override fun sing(){ } //재정의. super class를 그대로 재정의 하였으므로, 이 또한 open method가 된다.
    final override fun sing2(){ } //재정의. override 금지.
}

open class Animal611 : Animal61(){
    override fun sing(){ } //재정의. super class를 그대로 재정의 하였으므로, 이 또한 open method가 된다.
//    override fun sing2(){ } //재정의. override 금지. 오류발생
}
