package com.ssafy.ws_android_ui_layout_2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//MemoItem Class – data class : DTO
data class MemoItem(val title:String, val content:String, val regDate: String){
    override fun toString(): String {
        return "$title $regDate"
    }
}