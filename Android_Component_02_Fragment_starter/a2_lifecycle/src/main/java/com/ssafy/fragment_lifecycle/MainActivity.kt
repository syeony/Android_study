package com.ssafy.fragment_lifecycle

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.fragment_lifecycle.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainActivity_싸피"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        Log.d(TAG, "onCreate: $currentFragment")
        if (currentFragment == null) {
            /**** 1. add 호출 ****/
            /* 순차적으로 추가된다. */
//            supportFragmentManager.beginTransaction()
//                .add(
//                    R.id.fragment_container,
//                    AFragment.newInstance("초기 설정으로 추가", getFormattedDate())
//                ).commit()
//
//            supportFragmentManager.beginTransaction()
//                .add(
//                    R.id.fragment_container,
//                    BFragment.newInstance("초기 설정으로 추가", getFormattedDate())
//                ).commit()


            /**** 2. replace 호출 ****/
            /* AFragment 추가 후, BFragment가 추가될 때 AFragment는 onDestroy까지 호출된다. */
//            supportFragmentManager.beginTransaction()
//                .replace(
//                    R.id.fragment_container,
//                    AFragment.newInstance("초기 설정으로 추가", getFormattedDate())
//                ).commit()
//
//            supportFragmentManager.beginTransaction()
//                .replace(
//                    R.id.fragment_container,
//                    BFragment.newInstance("초기 설정으로 추가", getFormattedDate())
//                ).commit()

            /**** 3. replace + addToBackStack() 호출 ****/
            /* AFragment 추가 후, BFragment가 추가될 때 AFragment는 onDestroyView까지만 호출되고, onDestroy는 호출되지 않는다. */
            /* 뒤로 가기를 눌러보면, 기존 AFragment는 onCreate가 아니라 onCreateView 부터 불린다.(view만 만든다)
            BFragment는 onDestroy-onDetach순으로 불려서 해제된다.  */
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    AFragment.newInstance("초기 설정으로 추가", getFormattedDate())
                ).addToBackStack(null)
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    BFragment.newInstance("초기 설정으로 추가", getFormattedDate())
                ).addToBackStack(null)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
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