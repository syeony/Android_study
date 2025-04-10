package com.ssafy.service.c_aidl_binder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "AIDLService_싸피"
class AIDLService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }
    // AIDL로 정의해서 생성한 Stub() 구현
    private val myBinder = object :IMyAidlInterface.Stub(){
        override fun getCurrentTime(): String {
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.KOREA)
            date.timeZone = TimeZone.getTimeZone("Asia/Seoul")

            return "서비스 시간은: "+date.format(Date())
        }
    }




    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "onBind: ")
        //위에서 생성한 binder 리턴.
        return myBinder
    }
}

