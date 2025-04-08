package com.android.example.kotlinproject.c.kotlinIntensive

/* plus 연산자 구현하기 예제 */
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {  //"plus" 라는 연산자 함수를 정의합니다.
        return Point(x + other.x, y + other.y)
    }
}


fun main() {
    val point1 = Point(10, 20)
    val point2 = Point(30, 40)
    println(point1.plus(point2))  //"+"로 계산하면 "plus"함수가 호출됩니다.

    //times test
    val point = Point(10, 20)
    println("times 호출 : ${point * 1.5}") // times 호출

    //복합연산자
    var point3 = Point(1, 2)
    point3 += Point(3, 4)  // point = point + Point(3, 4)와 동일
    println("복합연산자 : ${point3}")  // 복합연산자 : Point(x=4, y=6)

    // 단항연산자 unaryMinus
    val point6 = Point(10, 20)
    println("단항 연산자(unaryMinus): ${-point6}") //// 단항 연산자(unaryMinus): Point(x=-10, y=-20)

    /* equals test */
    println(Point3(10, 20) == Point3(10, 20)) // true
    println(Point3(10, 20) != Point3(5, 5)) //true
    println(null == Point3(1, 2))  //false

    /* compareTo Test */
    val p1 = Person55("Alice", "Smith")
    val p2 = Person55("Bob", "Johnson")
    println ("compareTo Test : ${p1 < p2}") // compareTo Test : false

    // compareValuesBy 는 첫번째 비교에서 비교가 되면 리턴, 비교가 안되면 다음값 비교
    // 여기서는 lastname 비교, 같을경우는 firstname 비교
    val p3 = Person55("B", "A")
    val p4 = Person55("C", "A")
    println ("compareTo Test : ${p3 < p4}") // compareTo Test : true

}

/* 연산자를 확장 함수로 구현하기 */
operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}

/* 두 피연산자의 타입이 서로 다른 연산자 정의하기 */
operator fun Point.times(scale: Double): Point {
    return Point((x * scale).toInt(), (y * scale).toInt())
}

/* 단항 연산자 정의하기 */
operator fun Point.unaryMinus(): Point {  //단항연산자는 파라미터가 없습니다.
    return Point(-x, -y)  //각 좌표에 -(음수)를 취한 좌표를 반환
}

/* equals override*/
class Point3(private val x: Int, private val y: Int) {
    override fun equals(obj: Any?): Boolean {
        if (obj === this) return true
        if (obj !is Point3) return false
        return obj.x == x && obj.y == y
    }
}

/* 비교연산자 overloading :compareTo */
data class Person55( val firstName: String, val lastName: String ) : Comparable<Person55> {

    override fun compareTo(other: Person55): Int {
        // 첫번째 비교에서 비교가 되면 리턴, 비교가 안되면 다음값 비교. 아래 둘 동일.
        return compareValuesBy(
            this, other, Person55::lastName, Person55::firstName
//            this, other, {it -> it.lastName}, {it -> it.firstName}
        )

    }
}