package com.android.example.kotlinproject.b.kotlinadvanced

interface Clickable {
    fun click()
    fun showOff(){
        println("Clickable.showOff")
    }
}
interface Focusable{
    fun showOff(){
        println("Focusable.showOff")
    }
}
class Button : Clickable, Focusable {
    override fun click() {
        println("Button.click")
    }
    //showOff는 두 슈퍼에 모두 존재하므로, 동일하게 애매함을 없애기 위해서 반드시 선언.
    //super에 선언된 fun을 호출할때는 super<Clickable>.showOff()
    //java에서는 Clickable.super.showOff();
    override fun showOff() {
        println("Button.showOff")
    }
}

fun main() {
    var button = Button()
    button.click()
    button.showOff()
}
