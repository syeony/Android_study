package com.ssafy.exercise

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.exercise.databinding.ActivityEditBinding
import com.ssafy.exercise.dto.Memo
import com.ssafy.exercise.service.BoundService
import java.util.Date
import java.util.Locale

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    private var actionFlag = -1
    private var position = -1
    private lateinit var memo: Memo

    private var selectedDate: Long = System.currentTimeMillis() // 초기값 설정

    private var boundService: BoundService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            boundService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 전달받은 값들
        memo = intent.getSerializableExtra("memo") as Memo
        position = intent.getIntExtra("position", -1)
        actionFlag = intent.getIntExtra("actionFlag", -1)

        binding.calendarBtn.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.date_picker, null)
            val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)

            // 오늘 이후만 선택 가능하도록 설정
            datePicker.minDate = System.currentTimeMillis()

            AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("확인") { _, _ ->
                    val calendar = java.util.Calendar.getInstance().apply {
                        set(datePicker.year, datePicker.month, datePicker.dayOfMonth, 0, 0, 0)
                        set(java.util.Calendar.MILLISECOND, 0)
                    }
                    selectedDate = calendar.timeInMillis
                    binding.date.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedDate))
                }
                .setNegativeButton("취소", null)
                .show()
        }

        if (actionFlag == MainActivity.REGISTER) {
            binding.btnDelete.visibility = View.GONE
        } else {
            binding.btnDelete.visibility = View.VISIBLE

            binding.exercisetype.setText(memo.exerciseType)
            binding.minute.setText(memo.duration.toString())
            binding.memo.setText(memo.memo)
            selectedDate = memo.date
            binding.date.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedDate))
            binding.ratingbar.rating = memo.intensity
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.exercisetype.text.toString().trim()
            val minute = binding.minute.text.toString().trim()
            val day = binding.date.text.toString().trim()
            val rating = binding.ratingbar.rating
            val content = binding.memo.text.toString().trim()
            val created = System.currentTimeMillis()

            //data class Memo(val id:Int, val exerciseType:String, val duration:Int, val date:String, val intensity:Int, val memo:String, val createdAt: String)
            if (name.isNotEmpty() || minute.isNotEmpty() || day.isNotEmpty() || content.isNotEmpty()) {
                val item = Memo(-1, name, minute.toInt(), selectedDate, rating, content, created)

                if (actionFlag == MainActivity.REGISTER) { //추가
                    boundService?.add(item)
                } else { //수정
                    if (memo.id != -1) {
                        boundService?.update(item.copy(id = memo.id))
                    }
                }

                finish()
            } else {
                Toast.makeText(this, "형식에 맞게 모두 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener { //삭제
            AlertDialog.Builder(this)
                .setTitle("삭제 확인")
                .setMessage("정말 삭제하시겠습니까?")
                .setPositiveButton("확인") { _,_,->
                    if (memo.id != -1) {
                        boundService?.remove(memo.id)
                        finish()
                    }
                }
                .setNegativeButton("취소", null)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        val serviceIntent = Intent(this, BoundService::class.java)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}
