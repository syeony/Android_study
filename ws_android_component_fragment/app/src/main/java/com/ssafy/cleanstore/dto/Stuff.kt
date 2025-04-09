package com.ssafy.cleanstore.dto

import java.io.Serializable

data class Stuff(val name:String, val count:Int):Serializable {
    override fun toString(): String {
        return "물품 : $name -> 수량 : $count"
    }
}