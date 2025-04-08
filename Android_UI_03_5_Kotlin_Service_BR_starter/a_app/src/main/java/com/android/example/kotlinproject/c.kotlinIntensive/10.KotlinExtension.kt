package com.android.example.kotlinproject.c.kotlinIntensive

fun stringExtensionTest(){
    // 문자열 확장 함수
    fun String.lastChar(): Char {
        return this.get(this.length - 1)
    }

    println("Hello World".lastChar()) // d

    // 확장함수 추가 예제. lambda로 선언. lambda with receiver라고 부름.
    val plus5 : Int.() -> Int = {
        println("sum2 : this :${this.javaClass.name}")
        this.plus(5)
    }

    println(100.plus5())

    //----------------------------------
    val stringToInt: String.() -> Int = {
        println("String 확장함수 : $this")
        toInt()
    }

    println("123".stringToInt().javaClass.name)
}


fun staticExtension(){
    open class MyView {
        open fun click() = println("View clicked")
    }
    class MyButton: MyView() {
        override fun click() = println("Button clicked")
    }

    // 기능 확장
    fun MyView.showOff() = println("I'm a view!")
    fun MyButton.showOff() = println("I'm a button!")
    // 부모 타입으로 자식 객체 생성
    val view: MyView = MyButton()
    view.click() //Button clicked
    view.showOff() // I'm a view!
}


fun main() {

    stringExtensionTest() //d
    staticExtension();

}
