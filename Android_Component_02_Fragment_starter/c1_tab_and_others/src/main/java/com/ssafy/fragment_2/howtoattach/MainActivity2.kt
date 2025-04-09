package com.ssafy.fragment_2.howtoattach

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.fragment_2.R
import com.ssafy.fragment_2.databinding.ActivityMain2Binding

//Fragment를 layout xml에서 붙히는 방법 테스트
//  * FrameLayout (include 방식) or fragment

class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_blank2, BlankFragment1())
//            .commit()
    }
}