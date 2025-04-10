package com.ssafy.my_music_service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ssafy.my_music_service.databinding.ActivityMainBinding

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "서비스 테스트 예제"
        intent = Intent(this, MyMusicService::class.java)


        binding.startMusicBtn.setOnClickListener {
            startService(intent)
            Log.d(TAG, "onCreate: startService()")
            it.isClickable = false
            binding.stopMusicBtn.isClickable = true
        }

        binding.stopMusicBtn.setOnClickListener {
            stopService(intent);
            Log.d(TAG, "onCreate: StopService()")
            binding.startMusicBtn.isClickable = true
            it.isClickable = false
        }

        binding.foreBtn.setOnClickListener {
            startActivity(Intent(this, ForegroundActivity::class.java))
        }
    }
}