package com.ssafy.ws_android_ui_layout_2

import android.app.Activity
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

// EditActivity
private const val TAG = "AlarmReceiver_싸피"
class EditActivity : AppCompatActivity() {
    // DB 선언부
    private lateinit var memoDBHelper: MemoDBHelper
    private lateinit var dbActions: MemoDBActions

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                val item = MemoDto(0, title, content, date, tenMinChecked, thirtyMinChecked)

                var newId = intent.getIntExtra("_id", -1)
                if ("register" == mode) {
                    newId = dbActions.insertMemo(item).toInt()
                    Toast.makeText(this, "메모가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    if (newId != -1) {
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
                    Log.d(TAG, "checkMode: item")
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
}