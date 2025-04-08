package com.ssafy.alram

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.alram.databinding.ActivitySingleAlarmBinding

private const val TAG = "SingleAlarmAct_싸피"
class SingleAlarmActivity :AppCompatActivity(){

    private val binding: ActivitySingleAlarmBinding by lazy{
        ActivitySingleAlarmBinding.inflate(layoutInflater)
    }
    private lateinit var alarmManager:AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
    }

    override fun onResume() {
        super.onResume()
        checkStartPermissionRequest()
    }

    // 다른앱위에 그리기 권한이 있어야 있어야 다른앱 실행중에도 activity가 노출됨
    private fun checkStartPermissionRequest() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 1000)
            Toast.makeText(this, "권한을 허용해 주세요.", Toast.LENGTH_SHORT).show()
        } else {
            initEvent()
        }
    }

    private fun initEvent() {
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("key", "Hello")
        // requestCode 를 시간으로 설정
        val pendingIntent = PendingIntent.getBroadcast(this, SystemClock.elapsedRealtime().toInt(), intent, PendingIntent.FLAG_MUTABLE)

        binding.button0.setOnClickListener{
            val seconds = binding.seconds.text.toString().toInt()
            val triggerTime = (SystemClock.elapsedRealtime() + seconds * 1000) // 5초
            alarmManager.set(AlarmManager.ELAPSED_REALTIME, triggerTime, pendingIntent)

            Log.d(TAG, "알람등록")
            Toast.makeText(this, "알람등록", Toast.LENGTH_SHORT).show()
        }

        binding.button1.setOnClickListener {
            alarmManager.cancel(pendingIntent)
            Log.d(TAG, "알람 취소")
            Toast.makeText(this, "알람 취소", Toast.LENGTH_SHORT).show()
        }
    }
}