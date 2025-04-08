package com.ssafy.ws_android_ui_layout_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val TAG = "AlarmReceiver_싸피"
class MemoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent.key : ${intent.getStringExtra("content")}")

        Toast.makeText(context, "onReceive:${intent.getStringExtra("content")}", Toast.LENGTH_SHORT).show()

        // 메모 위치 가져오기
        val position = intent.getIntExtra("_id", -1)
        if (position != -1) {
            val memo = MemoItemMgr.search(position)
            memo.tenMinAlarm = false
            memo.thirtyMinAlarm = false

            // 변경된 체크 상태를 저장
            MemoItemMgr.update(position, memo)
        }
    }
}


//class MemoReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        Log.d(TAG, "Received intent.key : ${intent.getStringExtra("content")}")
//        Toast.makeText(context, "onReceive:${intent.getStringExtra("content")}", Toast.LENGTH_SHORT).show()
//    }
//}