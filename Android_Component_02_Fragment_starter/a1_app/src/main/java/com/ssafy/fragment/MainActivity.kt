package com.ssafy.fragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.fragment.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainActivity_싸피"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: ")

        //모든 액티비티는 프래그먼트를 꽂을 수 있는데
        //supportFragmentManager가 프래그먼트를 관리해주는 역할
        //현재 fragment 조회.
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(currentFragment == null){
            // add 로 AFragment 추가
            val aFragment=AFragment.newInstance("초기설정으로 추가",getFormattedDate())
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container,aFragment)
                .commit()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container,BFragment.newInstance("hi","hi"))
                .commit()
        }

        binding.removeBtn.setOnClickListener {
            val current = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if(current!=null){
                supportFragmentManager.beginTransaction()
                    .remove(current)
                    .commit()
            }
        }

        binding.fragABtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,AFragment.newInstance("",""))
                .commit()
        }

        binding.fragBBtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,BFragment.newInstance("",""))
                .commit()
        }
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

    fun getFormattedDate() : String {
        //format 설정
        val format = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale.KOREA)
        //TimeZone  설정 (GMT +9)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        //현재시간에 적용
        return format.format(Date().time)
    }
}