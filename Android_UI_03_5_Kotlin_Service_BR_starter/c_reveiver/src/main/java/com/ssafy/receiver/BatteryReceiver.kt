package com.ssafy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val TAG = "PhoneReceiver_싸피"
class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d(TAG, "PhoneReceiver.onReceive : ${p1?.action}")
    }
}