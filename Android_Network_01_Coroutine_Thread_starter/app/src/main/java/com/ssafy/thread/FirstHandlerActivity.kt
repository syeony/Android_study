package com.ssafy.thread

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.thread.databinding.ActivityFirstHandlerBinding

private const val TAG = "StartActivity_싸피"
class FirstHandlerActivity : AppCompatActivity() {

    private var isRunning = false
    lateinit var binding : ActivityFirstHandlerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener {
            startActivity(Intent(this, HandlerActivity::class.java))
        }

        isRunning = true
        val thread = ThreadClass()
        thread.start()
    }

    val handler = Handler(Looper.getMainLooper())
    inner class ThreadClass : Thread() {
        override fun run() {
            while(isRunning) {
                sleep(100)
                handler.post(object: Runnable{
                    override fun run(){
                        binding.helloTextview.text = System.currentTimeMillis().toString()
                    }
                })
            }
        }
    }

    override fun onRestart() {
        super.onRestart()

        isRunning = true
    }
    override fun onStop() {
        super.onStop()

        isRunning = false
    }
}















