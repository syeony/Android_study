package com.android.example.kotlinproject.b.kotlinadvanced

class Box<T>(t: T) {
    var value = t

    // 호출시점에 타입이 정해지는 제네릭을 인자로 받는 예

}

// kotlin
class Car2 {
}

// 항목을 담거나 뺄 수 있는 제네릭 인터페이스 Container 정의
interface Container<T> {
    fun put(item: T)
    fun take(index: Int) : T
}

// 자동차(Car)를 담거나 뺄 수 있는 클래스 Garage 정의
class Garage : Container<Car2> {
    private val items:MutableList<Car2> = arrayListOf();
    override fun put(item: Car2) {
        items.add(item)
    }

    override fun take(index:Int): Car2 {
        return items.get(index)
    }
}


fun main() {
    var car2 = Car2()
    var box = Box(car2)
    println(box.value)


    var car3 = Car2();

    var parkingLot = Garage()
    parkingLot.put(car2)
    parkingLot.put(car3)

    parkingLot.take(0)
    parkingLot.take(1)
}