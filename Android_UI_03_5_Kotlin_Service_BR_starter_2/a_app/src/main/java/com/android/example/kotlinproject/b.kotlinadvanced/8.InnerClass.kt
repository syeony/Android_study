package com.android.example.kotlinproject.b.kotlinadvanced

// nested class 중첩 클래스
class Outer {
    private val bar: Int = 1

    class Nested {
        // nested class는 외부 클래스 멤버 참조 불가
        // fun foo() = bar
        fun foo() = 2
    }
}


// inner class 내부 클래스
class OuterInner {
    private val bar: Int = 1

    inner class Inner {
        fun foo() = bar
//        fun foo() = this@OuterInner.bar
    }
}


fun main() {

    val demo = Outer.Nested().foo()
    val demo2 = OuterInner().Inner().foo()
    println(demo)  // 2
    println(demo2)  // 1
}

