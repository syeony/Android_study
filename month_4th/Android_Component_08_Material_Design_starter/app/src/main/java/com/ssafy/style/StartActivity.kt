package com.ssafy.style

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.style.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    lateinit var binding:ActivityStartBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.apply{
            text="사용자 정의 스타일 적용하기"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, HeaderActivity::class.java))
            }
        }

        binding.button2.apply{
            text="스타일 상속받아서 적용하기.기본."
            setOnClickListener {
                startActivity(Intent(this@StartActivity, ExtendedActivity::class.java))
            }
        }

        binding.button3.apply{
            text="스타일 상속받아서 적용하기. dot 으로 상속"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, Extended2Activity::class.java))
            }
        }

        binding.button4.apply{
            text="TextView에 Theme 적용하기"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, ThemeActivity::class.java))
            }
        }

        binding.button5.apply{
            text="Custom Theme 적용하기"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, CustomThemeActivity::class.java))
            }
        }

        binding.button6.apply{
            text="Custom Attr 적용하기"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, CustomAttrActivity::class.java))
            }
        }

    }
}