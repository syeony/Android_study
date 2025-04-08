package com.android.example.kotlinproject.c.kotlinIntensive

fun main() {

    val numbersMap = mapOf<String, String>("1" to "one", "2" to "two", "3" to "three")
    println("numbersMap: $numbersMap")
    val numbersMap2 = mapOf(Pair("1", "one"), Pair("2", "two"), Pair("3", "three"))
    println("numbersMap2: $numbersMap2")

// 실행해보면 모두 동일한 값을 갖고 있습니다.
// numbersMap: {1=one, 2=two, 3=three}
// numbersMap2: {1=one, 2=two, 3=three}


    val numbersMap3 = mapOf<String, String>(
        "1" to "one", "2" to "two", "3" to "three"
    )
    println("numbersMap.get(\"1\"): ${numbersMap3.get("1")}")
    println("numbersMap[\"1\"]: ${numbersMap3["1"]}")
    println("numbersMap keys:${numbersMap3.keys}")
    println("numbersMap values:${numbersMap3.values}")

    for (value in numbersMap3.keys) {
        println(value)
    }
    println("-----------")
    for (value in numbersMap3.values) {
        println(value)
    }
}