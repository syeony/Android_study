package com.ssafy.cleanstore.dto

import java.io.Serializable

data class Stuff(val id:Int, val name:String, val count:Int, val regDate:String, val rating:Int):Serializable {
    override fun toString(): String {
        return "물품 : $name -> 수량 : $count"
    }
}