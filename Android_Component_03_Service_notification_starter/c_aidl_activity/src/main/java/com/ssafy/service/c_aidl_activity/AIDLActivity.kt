package com.ssafy.service.c_aidl_activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.ssafy.service.c_aidl_activity.databinding.ActivityAidlactivityBinding
import com.ssafy.service.c_aidl_binder.IMyAidlInterface

private const val TAG = "AIDLActivity_싸피"
class AIDLActivity : AppCompatActivity() {
    private var timeService: IMyAidlInterface? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(TAG, "onServiceConnected: $p1 ")
            //binder 를 asInterface로 casting.
            timeService = IMyAidlInterface.Stub.asInterface(p1)

        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
            timeService = null
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        //Component Name 생성하고, bindService  호출.
        var target = ComponentName("com.ssafy.service.c_aidl_binder", "com.ssafy.service.c_aidl_binder.AIDLService")
        val intent = Intent().setComponent(target)

        bindService(intent, connection, BIND_AUTO_CREATE)


    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)

    }

    private lateinit var binding: ActivityAidlactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAidlactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener{
            val time = timeService?.getCurrentTime()?:"00:00"
            binding.textView2.text = "AIDL Service 호출. \n한국시간은 $time 입니다."
        }
    }


}

