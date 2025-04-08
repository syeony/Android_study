package com.ssafy.receiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ssafy.receiver.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.button_0).text = "정적리시버 - Manifest에 등록완료"
        findViewById<Button>(R.id.button_0).setOnClickListener{
        }

        findViewById<Button>(R.id.button_1).text = "동적리시버 구현"
        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent(this, BroadcastActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_2).text = "사용자정의 Broadcast"
        findViewById<Button>(R.id.button_2).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_3).text = "사용자정의 Broadcast with permission"
        findViewById<Button>(R.id.button_3).setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java);
            startActivity(intent)
        }

    }
}