package com.ssafy.cleanstore.dto

data class Stuff(val name:String, val count:String) {
    override fun toString(): String {
        return "물품 : $name -> 수량 : $count"
    }
}