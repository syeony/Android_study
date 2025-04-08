package com.ssafy.receiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.ssafy.receiver.R

private const val TAG = "MainActivity2_싸피"
class MainActivity2 : AppCompatActivity() {
    private lateinit var broadcastBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        broadcastBtn = findViewById(R.id.broadcast_btn)
        broadcastBtn.setOnClickListener {
            val intent = Intent()
            intent.action = "com.ssafy.android.news.funny"
            intent.putExtra("content", "티끌은 모아도 티끌이다.")
            // permission 사용자 대상으로 broadcast 발송
            sendBroadcast(intent, "com.ssafy.android.news.funny.PRIVATE")
            Log.d(TAG, "onCreate: 발송 완료")
        }
    }
}