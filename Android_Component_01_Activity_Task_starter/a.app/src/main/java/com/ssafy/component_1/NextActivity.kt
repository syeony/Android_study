package com.ssafy.component_1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.component_1.databinding.ActivityNextBinding

class NextActivity : AppCompatActivity() {

    lateinit var binding : ActivityNextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textview.text = "전달받은 데이터 : ${intent.getStringExtra("Key")}"

        binding.clickMe.setOnClickListener{
            val intent = Intent().apply{
                putExtra("to_main", "잘 받았습니다.")
            }

            // 결과전달
            setResult(Activity.RESULT_OK, intent)

            //현재 Activity 종료
            finish()
        }
    }
}