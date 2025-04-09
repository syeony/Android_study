package com.ssafy.cleanstore

import com.ssafy.cleanstore.dto.Stuff

object StuffMgr {
    private var stuffs = arrayListOf<Stuff>()

    fun getList(): ArrayList<Stuff>{
        return stuffs
    }

    fun size(): Int {
        return stuffs.size
    }

    fun add(item: Stuff) {
        stuffs.add(item)

    }

    fun search(index: Int): Stuff {
        return stuffs.get(index)
    }

    fun update(index: Int, item: Stuff) {
        if (index in stuffs.indices) {
            stuffs[index] = item
        }
    }

    fun remove(index: Int) {
        if (index in stuffs.indices) {
            stuffs.removeAt(index)
        }
    }

    fun clear() {
        stuffs.clear()
    }
}