package com.ssafy.service.a_local_binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.service.a_local_binder.databinding.ActivityBindBinding

private const val TAG = "BindActivity_싸피"

class BindActivity : AppCompatActivity() {

    private lateinit var myService: BoundService

    private val connection = object:ServiceConnection{
        //서비스 연결됐을때
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            //타입캐스팅
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()
        }
        //서비스 끊어졌을때
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
        }

    }

    private lateinit var binding : ActivityBindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.textView.text=myService.getCurrentTime()
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundService::class.java)
        //호출하면 결과는 ServiceConnection.onServiceConnected로 callback
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }



}