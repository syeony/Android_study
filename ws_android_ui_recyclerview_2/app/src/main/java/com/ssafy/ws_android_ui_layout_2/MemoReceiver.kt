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
        val position = intent.getIntExtra("position", -1)
        if (position != -1) {
            // 데이터베이스 접근을 위한 헬퍼 및 액션 객체 생성
            val memoDBHelper = MemoDBHelper(context, "newdb.db", null, 1)
            val dbActions = MemoDBActions(memoDBHelper.writableDatabase)

            // 데이터베이스에서 메모 조회
            val memo = dbActions.selectMemo(position)
            if (memo != null) {
                memo.tenMinAlarm = false
                memo.thirtyMinAlarm = false

                // 변경된 체크 상태를 저장
                dbActions.updateMemo(memo)
                Log.d(TAG, "Memo updated: id=$position, tenMinAlarm=false, thirtyMinAlarm=false")
            } else {
                Log.e(TAG, "Memo not found for id: $position")
            }

            // DB 연결 닫기
            memoDBHelper.close()
        }
    }
}


//private const val TAG = "AlarmReceiver_싸피"
//class MemoReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//
//        Log.d(TAG, "Received intent.key : ${intent.getStringExtra("content")}")
//
//        Toast.makeText(context, "onReceive:${intent.getStringExtra("content")}", Toast.LENGTH_SHORT).show()
//
//        // 메모 위치 가져오기
//        val position = intent.getIntExtra("_id", -1)
//        if (position != -1) {
//            val memo = MemoItemMgr.search(position)
//            memo.tenMinAlarm = false
//            memo.thirtyMinAlarm = false
//
//            // 변경된 체크 상태를 저장
//            MemoItemMgr.update(position, memo)
//        }
//    }
//}


//class MemoReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        Log.d(TAG, "Received intent.key : ${intent.getStringExtra("content")}")
//        Toast.makeText(context, "onReceive:${intent.getStringExtra("content")}", Toast.LENGTH_SHORT).show()
//    }
//}