package com.ssafy.cleanstore

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.cleanstore.databinding.ActivityMainBinding
import com.ssafy.cleanstore.stuff.StuffActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ssafybucks.setOnClickListener{
            val intent = Intent(this, StuffActivity::class.java)
            startActivity(intent)
        }

    }

}