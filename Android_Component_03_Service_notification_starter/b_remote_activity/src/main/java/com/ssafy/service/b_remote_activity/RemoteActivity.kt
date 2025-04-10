package com.ssafy.service.b_remote_activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.service.b_remote_activity.databinding.ActivityRemoteBinding

class RemoteActivity : AppCompatActivity() {
    private var myService: Messenger? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            myService = Messenger(p1)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            myService = null
        }
    }

    override fun onStart() {
        super.onStart()
        // 서비스 바인딩 처리
        val target = ComponentName("com.ssafy.service.b_remote_binder", "com.ssafy.service.b_remote_binder.MyRemoteService")
        Intent().setComponent(target).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

    private lateinit var binding: ActivityRemoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            sendMessage()
        }
    }
    private fun sendMessage() {
        val msg = Message.obtain()
        msg.data = Bundle().apply { putString("MyString", "This is Message") }
        // 서비스의 사용
        myService?.send(msg)

    }
}













