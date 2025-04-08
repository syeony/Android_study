package com.ssafy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val TAG = "SSAFYNewsReceiver2_싸피"
class SSAFYNewsReceiver2 : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: receive 받기: ${intent.action}")
        Log.d(TAG, "onReceive: extra: ${intent.getStringExtra("content")}")
    }
}