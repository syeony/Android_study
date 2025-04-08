package com.example.app4_drawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app4_drawer.databinding.ActivityIncludeBinding

class IncludeActivity: AppCompatActivity() {

    lateinit var binding: ActivityIncludeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIncludeBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}