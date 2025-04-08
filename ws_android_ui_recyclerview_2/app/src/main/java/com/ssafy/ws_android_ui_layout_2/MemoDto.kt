package com.ssafy.ws_android_ui_layout_2

//MemoItem Class – data class : DTO
data class MemoDto(val _id: Int, val title:String="hi", val content:String, val regDate: String, var tenMinAlarm: Boolean, var thirtyMinAlarm:Boolean){

    override fun toString(): String {
        return "$title $regDate"
    }
}