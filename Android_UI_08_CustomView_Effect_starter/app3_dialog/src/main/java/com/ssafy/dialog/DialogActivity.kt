package com.ssafy.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val tv1 = findViewById<TextView>(R.id.tv1)

        // 1. 기본 다이얼로그
        btn1.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("기본 다이얼로그")
            builder.setMessage("기본 다이얼로그")
            builder.setIcon(R.mipmap.ic_launcher)

            // 버튼 클릭시에 무슨 작업을 할 것인가!
            val listener = DialogInterface.OnClickListener { p0, p1 ->
                when (p1) {
                    DialogInterface.BUTTON_POSITIVE ->
                        tv1.text = "BUTTON_POSITIVE"
                    DialogInterface.BUTTON_NEUTRAL ->
                        tv1.text = "BUTTON_NEUTRAL"
                    DialogInterface.BUTTON_NEGATIVE ->
                        tv1.text = "BUTTON_NEGATIVE"
                }
            }
            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", listener)
//            builder.setNeutralButton("Neutral", listener)
            builder.show()
        }

        // 2. 커스텀 다이얼로그
        btn2.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("커스텀 다이얼로그")
            builder.setIcon(R.mipmap.ic_launcher)

            val v1 = layoutInflater.inflate(R.layout.dialog, null)
            builder.setView(v1)
            // p0에 해당 AlertDialog가 들어온다. findViewById를 통해 view를 가져와서 사용
            val listener = DialogInterface.OnClickListener { dialogInterface, which ->
                val alert = dialogInterface as AlertDialog
                val edit1: EditText = alert.findViewById(R.id.editText)
                val edit2: EditText = alert.findViewById(R.id.editText2)

                tv1.text = "${edit1.text}"
                tv1.append("${edit2.text}")
            }
            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", null)
            builder.show()
        }

        // 3. 날짜 다이얼로그
        btn3.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                // i년 i2월 i3일
                tv1.text = "${year}년 ${month + 1}월 ${day}일"
            }

            val picker = DatePickerDialog(this, listener, year, month, day)

            //최소선택기준 정해줌
            val min = Calendar.getInstance()
            min.set(2025, 3, 3) // 2025.04.03 ~

            //최대선택기준 정해줌
            val max = Calendar.getInstance()
            max.set(2025, 3, 22) // ~ 2025.04.22

            picker.datePicker.minDate = min.timeInMillis
            picker.datePicker.maxDate = max.timeInMillis

            picker.show()
        }


        // 4. 시간 다이얼로그
        btn4.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            val listener = TimePickerDialog.OnTimeSetListener { timePicker, hour, min ->
                tv1.text = "${hour}시 ${min}분"
            }

            val picker = TimePickerDialog(this, listener, hour, minute, false)
            // true하면 24시간제
            picker.show()
        }


        findViewById<Button>(R.id.btn_next).setOnClickListener {
            startActivity(Intent(this, DialogActivity2::class.java))
        }
    }

}
