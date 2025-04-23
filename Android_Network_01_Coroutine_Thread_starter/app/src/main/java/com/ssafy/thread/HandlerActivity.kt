package com.ssafy.thread

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.thread.databinding.ActivityHandlerBinding

class HandlerActivity : AppCompatActivity() {

    private val dogImageList = ArrayList<Int>()

    lateinit var binding : ActivityHandlerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startActivity(Intent(this, TimerActivity::class.java))
        }

        for(i in 1..30) {
            dogImageList.add(R.drawable.dog1)
            dogImageList.add(R.drawable.dog2)
            dogImageList.add(R.drawable.dog3)
        }

        val handler = Handler(Looper.getMainLooper())
        Thread {
            for(i in dogImageList) {
                Thread.sleep(1000)
                handler.post {
                    binding.dogIv.setImageResource(i)
                }
            }
        }.start()
    }
}















