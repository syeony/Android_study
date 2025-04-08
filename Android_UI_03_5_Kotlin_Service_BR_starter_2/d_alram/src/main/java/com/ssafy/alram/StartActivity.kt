package com.ssafy.alram

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.button_0).setOnClickListener{
            val intent = Intent(this, SingleAlarmActivity::class.java);
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_1).setOnClickListener{
            val intent = Intent(this, RepeatAlarmActivity::class.java);
            startActivity(intent)
        }
    }
}