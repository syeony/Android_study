package com.ssafy.googlemap

data class Item(val type:String, val title:String, val latitude:Double, val longitude:Double)

enum class Type{
    RESTAURANT, BANK, CAFE
}