package com.ssafy.ws_android_ui_layout_2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object{
        fun getDateString() : String {
            val formatter = SimpleDateFormat("MM-dd HH:mm", Locale.KOREAN)
            formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")

            return formatter.format(System.currentTimeMillis())
        }
    }
}