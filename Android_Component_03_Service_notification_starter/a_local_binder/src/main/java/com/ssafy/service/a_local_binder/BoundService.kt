package com.ssafy.service.a_local_binder

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val TAG = "BoundService_싸피"
class BoundService: Service() {

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind: ")
        return MyLocalBinder()
    }

    inner class MyLocalBinder : Binder(){
        fun getService(): BoundService {
            return this@BoundService
        }
        //내가 밖에 있는걸 선언하려면 inner붙여야함
    }

    fun getCurrentTime():String{
        val dateformat = SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.KOREA)
        dateformat.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        return dateformat.format(Date())
    }

}