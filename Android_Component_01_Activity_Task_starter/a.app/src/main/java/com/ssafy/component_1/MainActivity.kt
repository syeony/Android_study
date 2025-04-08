package com.ssafy.component_1

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.component_1.databinding.ActivityMainBinding

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailButton.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
            startActivity(intent)
        }

        binding.explicitButton.setOnClickListener{
            Toast.makeText(this,"명시적 인텐트", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, NextActivity::class.java)
            intent.putExtra("Key", "MainActiviy에서 명시적 인텐트 전달")
            startActivity(intent)

//            startActivity(Intent(this,NextActivity::class.java).apply {
//                putExtra("Key", "MainActiviy에서 명시적 인텐트 전달")
//            })
        }

        //startActivity를 쓰면 값을 다시 못받아오기때문에 이 laucher를 써야함
        //너무 길고 복잡해서 못외운다고 하셨음
        val requestActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK) {
                val intent = it.data
                val returnValue = intent?.getStringExtra("to_main")
                Toast.makeText(this, returnValue, Toast.LENGTH_SHORT).show();
            }
        }

        binding.explicit2Button.setOnClickListener{
            Toast.makeText(this,"명시적 인텐트-결과받기", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, NextActivity::class.java)
            intent.putExtra("Key", "MainActiviy에서 명시적 인텐트 전달, 결과를 넘겨주세요.")
            //결과 돌려받기.
            requestActivity.launch(intent)
        }

        binding.implicitButton.setOnClickListener{
            Toast.makeText(this,"암시적 인텐트", Toast.LENGTH_SHORT).show()

            //암시적 인텐트 목적에 맞는 호출 : 지도보기, 연락처보기, 인터넷, SNS 공유 등등.
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"))
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:36.222,128.111111"))
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-1111-2222"))
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"))

            startActivity(intent)
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, PhoneActivity::class.java))
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