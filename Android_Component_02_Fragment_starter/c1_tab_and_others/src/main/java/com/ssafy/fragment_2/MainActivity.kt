package com.ssafy.fragment_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.fragment_2.databinding.ActivityMainBinding
import com.ssafy.fragment_2.howtoattach.MainActivity2
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainActivity_싸피"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.attachBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }

        binding.buttonTab.setOnClickListener {
            val intent = Intent(this, TabLayoutActivity::class.java)
            startActivity(intent)
        }

        binding.buttonPager2.setOnClickListener {
            startActivity(Intent(this, TabLayoutPagerActivity::class.java))
        }

        Log.d(TAG, "onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

}