package com.ssafy.alram

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.SystemClock
import android.util.Log


private const val TAG = "BootReceiver_싸피"
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "BootReceiver.onReceive intent : $intent")
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ) {
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

            val broadIntent = Intent(context, AlarmReceiver::class.java)
            broadIntent.putExtra("key", "BootReceiver.반복")

            val pendingIntent = PendingIntent.getBroadcast(context, 1, broadIntent, PendingIntent.FLAG_MUTABLE)
            val repeatInterval:Long = 60 * 1000 //1분 이내로 설정하면 1분으로 동작함.
            val triggerTime = (SystemClock.elapsedRealtime() + repeatInterval)
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerTime, repeatInterval, pendingIntent)

            Log.d(TAG, "BootReceiver.반복 알람 등록")
        }
    }
}