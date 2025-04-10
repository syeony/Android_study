package com.ssafy.ws_android_ui_layout_2

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.ws_android_ui_layout_2.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.content.Context
import android.os.SystemClock

// EditActivity
private const val TAG = "AlarmReceiver_싸피"
class EditActivity : AppCompatActivity() {
    // DB 선언부
    private lateinit var memoDBHelper: MemoDBHelper
    private lateinit var dbActions: MemoDBActions

    private lateinit var binding: ActivityEditBinding
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        // helper인스턴스 생성
        memoDBHelper = MemoDBHelper(this, "newdb.db", null, 1)
        dbActions = MemoDBActions(memoDBHelper.writableDatabase)
        Log.d(ContentValues.TAG, "onCreate: 생성된 DB 정보: $dbActions")

        binding.btnCancel.setOnClickListener {
            finish()
        }

        val mode = intent.getStringExtra("mode")
        checkMode(mode)
        initEvent(mode)
    }

    private fun initEvent(mode: String?) {
        binding.btnDelete.setOnClickListener{
            val _id=intent.getIntExtra("_id", -1)
            dbActions.deleteMemo(_id)
            finish()
            Toast.makeText(this, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.btnSave.setOnClickListener {
//            val _id=intent.getIntExtra("_id", -1)
            val title = binding.title.text.toString().trim()
            val content = binding.content.text.toString().trim()
            val date = getCurrentDate()
            val tenMinChecked = binding.chkTenMin.isChecked
            val thirtyMinChecked = binding.chkThirtyMin.isChecked

            if (title.isNotEmpty() && content.isNotEmpty()) {
                var newId = intent.getIntExtra("_id", -1)
                val item = MemoDto(newId, title, content, date, tenMinChecked, thirtyMinChecked)

//                var newId = intent.getIntExtra("_id", -1)
                if ("register" == mode) {
                    newId = dbActions.insertMemo(item).toInt()
                    Toast.makeText(this, "메모가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    if (newId != -1) {
                        Log.d(TAG, "뉴아이디: $newId")
                        dbActions.updateMemo(item)
                        Toast.makeText(this, "메모가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                val intent = Intent()
                intent.apply {
                    putExtra("_id", newId)
                    putExtra("tenMinAlarm", tenMinChecked)
                    putExtra("thirtyMinAlarm", thirtyMinChecked)
                }
                Log.d(TAG, "initEvent: $newId")

                setAlarms(newId, tenMinChecked, thirtyMinChecked)

                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "모든 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkMode(mode: String?) {
        if ("register" == mode) {
            binding.chkTenMin.isChecked = false
            binding.chkThirtyMin.isChecked = false
            binding.regDate.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
        } else { //수정모드
            val position = intent.getIntExtra("_id", -1)
            if (position != -1) {
                Log.d(TAG, "checkMode: $position")
                val item = dbActions.selectMemo(position)
                if (item != null) {
                    Log.d(TAG, "checkMode: $item")
                    binding.title.setText(item.title)
                    binding.content.setText(item.content)
                    binding.regDate.setText(item.regDate)
                    binding.chkTenMin.setChecked(item.tenMinAlarm)
                    binding.chkThirtyMin.setChecked(item.thirtyMinAlarm)
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun setAlarms(position: Int, tenMinAlarm: Boolean, thirtyMinAlarm: Boolean) {
        Log.d(TAG, "setAlarms: $position")
        if (position == -1) return
//        val memo = MemoItemMgr.search(position)
        val memo = dbActions.selectMemo(position)

        if (tenMinAlarm){
            if (memo != null) {
                registerAlarm(position, 10, memo.content)
            }
        } else {
            cancelAlarm(position, 10)
        }

        if (thirtyMinAlarm){
            if (memo != null) {
                registerAlarm(position, 30, memo.content)
            }
        } else {
            cancelAlarm(position, 30)
        }
    }

    private fun registerAlarm(position: Int, minutes: Int, content: String) {
        val intent = Intent(this, MemoReceiver::class.java).apply {
            putExtra("position", position)
            putExtra("content", content)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            position + minutes,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        val triggerTime = SystemClock.elapsedRealtime() + 3000 //minutes * 60 * 1000
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, triggerTime, pendingIntent)
        Log.d(TAG, "$minutes 분 후 알람이 등록되었습니다.")
    }

    private fun cancelAlarm(position: Int, minutes: Int) {
        val intent = Intent(this, MemoReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            position + minutes,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
        Log.d(TAG, "$minutes 분 후 알람이 취소되었습니다.")
    }
}
