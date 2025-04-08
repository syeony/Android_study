package com.android.example.kotlinproject.c.kotlinIntensive

data class Person(val name: String, val age: Int)

fun main() {
    filterTest()
//    mapTest()
//    mapWithFilterTest()
//    mapToMap()
//    reduceAndFoldTest()
//    allAnyTest()
//    countTest()
//    findTest()
//    groupByTest()
//    flatMapTest()
//    flattenTest()
}

fun filterTest() {
    /* 리스트에서 짝수만 뽑아내는 예제 */
    val list = listOf(1, 2, 3, 4)
    println(list.filter { it % 2 == 0 }) //짝수만 필터링


    /* 나이가 30살 이상인 사람만 뽑아내는 예제 */
    val people = listOf(Person("안드로이드", 29), Person("코틀린", 30))
    val filtered = people.filter { it.age >= 30 }
    filtered.forEach {
        println(it) // Person(name=코틀린, age=30)
    }
}

fun mapTest() {
    // 각 원소의 제곱으로 모인 리스트를 만드는 map 예제
    val list = listOf(1, 2, 3, 4)
    println(list.map { it * it }) //제곱 만들기 (1x1, 2x2, 3x3, 4x4)

    // 사람 리스트 -> 이름 리스트 변환
    val people = listOf(Person("안드로이드", 29), Person("코틀린", 30))
    val mapped = people.map { it -> it.name }
    mapped.forEach {
        println(it)
    }
    // 안드로이드
    // 코틀린
}

fun mapWithFilterTest() {
    // 나이 30 이상인 사람의 이름 목록
    val peoples = listOf(Person("안드로이드", 29), Person("코틀린", 30))
    println(peoples.filter { it.age >= 30 }.map { it.name }) //[코틀린]

    // 나이가 가장 많은 사람의 이름을 알고 싶은 경우
    // filter '내부'에서 maxBy 이용 [비효율적 코드] - 변하지 않는 최대값을 자료 수 만큼 구함
    peoples.filter { it.age == peoples.maxByOrNull(Person::age)!!.age }

    // filter '외부'에서 maxBy 이용 [효율적 코드]
    val maxAge = peoples.maxByOrNull(Person::age)!!.age
    val maxAgePeople = peoples.filter { it.age == maxAge }
    println(maxAgePeople) // [Person(name=코틀린, age=30)]
}

fun mapToMap() {
    val numbers = mapOf(1 to "zero", 2 to "one")
    // key는 제곱수로, value는 대문자로 변환
    val newMap = numbers.mapValues { it.value.uppercase() }.mapKeys { it.key * it.key }
    newMap.forEach {
        println("${it.key} - ${it.value}")
    }
//    1 - ZERO
//    4 - ONE
}

fun reduceAndFoldTest(){
    // 컬렉션 내의 데이터를 모으기. 시작이 첫 원소부터
    val list = listOf(1, 2, 3, 4)
    val sum = list.reduce { acc, i -> acc + i } //누적, 현재원소 -> 수행
    println(sum) // 10


    // 컬렉션 내의 데이터를 모으기. 시작이 initial Value부터
    val list2 = listOf(1, 2, 3, 4)
    val sum2 = list2.fold(10) { acc, i -> acc + i } //누적, 현재원소 -> 수행
    println(sum2) // 20

}


fun allAnyTest() {
    // 조건에 만족하는지 검사하는 Predicate 계열의 함수
    val under30 = { p: Person -> p.age < 30 }

    //모든 원소가 만족하는지 판단하려면 all 함수를 사용
    val people3 = listOf(Person("안드로이드", 25), Person("코틀린", 33))
    println(people3.all(under30))  //false

    //하나라도 만족하는 원소가 있는지 판단하려면 any 함수를 사용
    println(people3.any(under30)) //true
}

fun countTest() {
    val under30 = { p: Person -> p.age < 30 }

    val people5 = listOf(Person("안드로이드", 25), Person("코틀린", 33))
    println(people5.count(under30)) // 1
}

fun findTest() {
    val under40 = { p: Person -> p.age < 40 }
    val people7 = listOf(Person("안드로이드", 25), Person("코틀린", 33))
    println(people7.find(under40)) //Person(name=안드로이드, age=25)

    val under10 = { p: Person -> p.age < 10 }
    println(people7.find(under10)) // null
}

fun groupByTest() {
    val people8 = listOf(
        Person("안드로이드", 25),
        Person("코틀린", 30),
        Person("자바", 30)
    )
    val map = people8.groupBy { it.age }
    map.forEach {
        println("나이: ${it.key}, 구성원: ${it.value}")
    }
//    나이: 25, 구성원: [Person(name=안드로이드, age=25)]
//    나이: 30, 구성원: [Person(name=코틀린, age=30), Person(name=자바, age=30)]
}

fun flatMapTest(){
    val strings = listOf("abc", "def")
    val newMap = strings.flatMap{
        it.toList()
    }
    println(newMap) //[a, b, c, d, e, f]

    val nestedList = listOf(listOf("abc", "def"), listOf("hij", "klm"))
    val newMap2 = nestedList.flatMap{ it.toList() }
    println(newMap2) //[abc, def, hij, klm]

    val newMap3 = nestedList.flatMap{ it }.flatMap { it.toList() }
    println(newMap3) //[a, b, c, d, e, f, h, i, j, k, l, m]
}

fun flattenTest(){
    val strings = listOf(listOf("abc", "def"), listOf("hij", "klm"))

    println(strings.flatten()) //[abc, def, hij, klm]
}

