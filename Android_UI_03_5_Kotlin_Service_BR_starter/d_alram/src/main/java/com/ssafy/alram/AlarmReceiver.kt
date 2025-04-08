package com.ssafy.alram

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val TAG = "AlarmReceiver_싸피"
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent : $intent")
        Log.d(TAG, "Received intent.key : ${intent.getStringExtra("key")}")
        Toast.makeText(context, "Received intent:${intent.getStringExtra("key")}", Toast.LENGTH_SHORT).show()

        // BR에서 Activity 호출하기.
//        val it = Intent(context, AlarmMainActivity::class.java)
//        //BR의 context는 TASK정보가 없으므로 새로 만든다.
//        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(it)
    }
}