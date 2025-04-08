package com.ssafy.music.myservice

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "서비스 테스트 예제"
//      val intent = Intent(this, MyMusicService::class.java)

        // 알림 허용 팝업권한 체크 & 팝업 띄우기.
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
        Log.d(TAG, "onCreate: $permission")
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
        }
        val intent = Intent(this, MyMusicForegroundService::class.java)

        btnStart = findViewById(R.id.start_music_btn)
        btnStop = findViewById(R.id.stop_music_btn)

        btnStart.setOnClickListener {
            intent.putExtra("hello", "from Activity")
            startService(intent)
            Log.d(TAG, "onCreate: startService()")
        }

        btnStop.setOnClickListener {
            stopService(intent);
            Log.d(TAG, "onCreate: StopService()")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}