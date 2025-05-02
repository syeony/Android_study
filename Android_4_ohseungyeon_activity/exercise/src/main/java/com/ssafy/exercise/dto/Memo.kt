package com.ssafy.exercise.dto

import java.io.Serializable

data class Memo(val id:Int, val exerciseType:String, val duration:Int, val date:Long, val intensity:Float, val memo:String, val createdAt: Long):
    Serializable {

}