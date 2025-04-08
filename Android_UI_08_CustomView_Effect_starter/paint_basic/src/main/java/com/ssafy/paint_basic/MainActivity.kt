package com.ssafy.paint_basic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.toolbar.paint_basic.databinding.ActivityMainBinding


private const val TAG = "DrawActivity_싸피"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}