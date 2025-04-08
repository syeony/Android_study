package com.ssafy.flag

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.flag.databinding.ActivitySub2Binding

private const val TAG = "SubActivity2_싸피"
class SubActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySub2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySub2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCallSub1.setOnClickListener {
            val intent = Intent(this, SubActivity1::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP  or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
        }
        Log.d(TAG, "SubActivity2.onCreate: ${System.identityHashCode(this)}")
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "SubActivity2.onStart: ${System.identityHashCode(this)}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "SubActivity2.onResume: ${System.identityHashCode(this)}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "SubActivity2.onPause: ${System.identityHashCode(this)}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "SubActivity2.onStop: ${System.identityHashCode(this)}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SubActivity2.onDestroy: ${System.identityHashCode(this)}")
    }


}
