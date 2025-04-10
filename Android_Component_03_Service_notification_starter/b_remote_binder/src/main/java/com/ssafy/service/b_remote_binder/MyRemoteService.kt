package com.ssafy.service.b_remote_binder

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MyRemoteService : Service() {

    // Handler를 이용해 만든 Messenger를 이용해 클라이언트에 반환될 iBinder 객체 반환한다.
    override fun onBind(intent: Intent): IBinder {
        val messenger = Messenger(object : Handler( Looper.myLooper()!! ) {
            // 메시지를 수신했을 때 처리할 동작 구현
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val data = msg.data
                val str = data.getString("MyString")
                Toast.makeText(applicationContext, "${str} \n ${getFormattedDate()}" , Toast.LENGTH_SHORT).show()
            }

            private fun getFormattedDate() : String {
                val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale.KOREAN) //format 설정
                format.timeZone = TimeZone.getTimeZone("Asia/Seoul") //TimeZone  설정 (GMT +9)

                //현재시간에 적용
                return format.format(Date().time)
            }
        })
        return messenger.binder
    }

}