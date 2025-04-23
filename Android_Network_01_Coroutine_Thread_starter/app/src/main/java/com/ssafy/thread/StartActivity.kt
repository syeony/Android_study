package com.ssafy.thread

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.thread.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private val binding:ActivityStartBinding by lazy{
        ActivityStartBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button1.apply{
            text="첫번째 Thread 만들기"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, FirstThreadActivity::class.java))
            }
        }
        binding.button2.apply{
            text="Handler로 처리"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, FirstHandlerActivity::class.java))
            }
        }
        binding.button3.apply{
            text="Handler Activity"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, HandlerActivity::class.java))
            }
        }
        binding.button4.apply{
            text="Timer Activity"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, TimerActivity::class.java))
            }
        }
        binding.button5.apply{
            text="RunOnUiThreadActivity"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, RunOnUiThreadActivity::class.java))
            }
        }
        binding.button6.apply{
            text="AsyncTaskActivity"
            setOnClickListener {
                startActivity(Intent(this@StartActivity, AsyncTaskActivity::class.java))
            }
        }
    }
}