package com.ssafy.thread

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.thread.databinding.ActivityFirstThreadBinding

private const val TAG = "StartActivity_싸피"
class FirstThreadActivity : AppCompatActivity() {

    private var isRunning = false
    lateinit var binding : ActivityFirstThreadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstThreadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener {
            startActivity(Intent(this, FirstHandlerActivity::class.java))
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
                Log.d(TAG, System.currentTimeMillis().toString())

                handler.post { //SAM
                    binding.helloTextview.text=System.currentTimeMillis().toString() //main에서 수행
                }

//                binding.helloTextview.text = System.currentTimeMillis().toString()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        isRunning = false
    }
}















