package com.ssafy.alram

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "RepeatAlarmAct_싸피"
class RepeatAlarmActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repeat_alarm)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("key", "Hello")
        val pendingIntent = PendingIntent.getBroadcast(this, SystemClock.elapsedRealtime().toInt(), intent, PendingIntent.FLAG_MUTABLE)

        val repeatInterval: Long = AlarmManager.INTERVAL_FIFTEEN_MINUTES
//        val repeatInterval: Long = 60 * 1000 //1분 이내로 설정하면 1분으로 동작함.

        findViewById<Button>(R.id.button_0).setOnClickListener{
            val triggerTime = (SystemClock.elapsedRealtime() + repeatInterval)
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, pendingIntent)
//            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerTime, repeatInterval, pendingIntent)

            Log.d(TAG, "반복 알람 등록")
            Toast.makeText(this, "반복 알람등록", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.button_1).setOnClickListener {
            alarmManager.cancel(pendingIntent)
            Log.d(TAG, "반복 알람 취소")
            Toast.makeText(this, "반복 알람 취소", Toast.LENGTH_SHORT).show()
        }
    }
}