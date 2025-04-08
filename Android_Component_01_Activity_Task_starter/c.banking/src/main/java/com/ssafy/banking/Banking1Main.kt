package com.ssafy.banking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ssafy.banking.databinding.ActivityBanking1Binding

class Banking1Main : AppCompatActivity() {
    private lateinit var binding: ActivityBanking1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBanking1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAccountInfo.setOnClickListener {
            startActivity(Intent(this, Banking2Account::class.java))
        }
    }
}