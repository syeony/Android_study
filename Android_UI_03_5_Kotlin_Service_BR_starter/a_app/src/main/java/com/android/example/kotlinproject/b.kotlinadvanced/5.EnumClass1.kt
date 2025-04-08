package com.android.example.kotlinproject.b.kotlinadvanced


enum class SsafyHandsome {
    SSAFY, LOVE, PEACE
}

fun main() {
    val ssafyEnum: SsafyHandsome = SsafyHandsome.SSAFY
    println("${ssafyEnum.name} ... ${ssafyEnum.ordinal}")

    val ssafyEnum2: Array<SsafyHandsome> = SsafyHandsome.values()

    for (i in ssafyEnum2.indices) {
        println(ssafyEnum2[i].name)
    }

}

