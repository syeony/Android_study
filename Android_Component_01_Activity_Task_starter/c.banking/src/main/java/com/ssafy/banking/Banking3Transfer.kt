package com.ssafy.banking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.banking.databinding.ActivityBanking3Binding

private const val TAG = "Banking3Transfer_싸피"
class Banking3Transfer : AppCompatActivity() {
    private lateinit var binding:ActivityBanking3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBanking3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Banking3Transfer.onCreate: ")

        binding.btnTransfer2.setOnClickListener {
            val intent = Intent(this, Banking4Result::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Banking3Transfer.onStart: " + System.identityHashCode(this))
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Banking3Transfer.onResume: " + System.identityHashCode(this))
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Banking3Transfer.onPause: " + System.identityHashCode(this))
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Banking3Transfer.onStop: " + System.identityHashCode(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Banking3Transfer.onDestroy: " + System.identityHashCode(this))
    }
}