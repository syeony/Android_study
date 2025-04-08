package com.ssafy.receiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.ssafy.receiver.R

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    private lateinit var broadcastBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        broadcastBtn = findViewById(R.id.broadcast_btn)
        broadcastBtn.setOnClickListener {
            val intent = Intent(this, SSAFYNewsReceiver::class.java)
            intent.action = "example.MY"
            intent.putExtra("content", "오늘도 티끌을 모아봅니다.")

            sendBroadcast(intent)
            Log.d(TAG, "onCreate: 발송 완료")
        }
    }
}