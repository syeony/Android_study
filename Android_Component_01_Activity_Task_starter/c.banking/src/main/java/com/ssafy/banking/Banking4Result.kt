package com.ssafy.banking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.banking.databinding.ActivityBanking4Binding

private const val TAG = "Banking4Result_싸피"
class Banking4Result : AppCompatActivity() {
    private lateinit var binding: ActivityBanking4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBanking4Binding.inflate(layoutInflater)

        setContentView(binding.root)
        Log.d(TAG, "Banking4Result.onCreate: " + System.identityHashCode(this))

        binding.btnAccountInfo3.setOnClickListener {
            val intent = Intent(this, Banking2Account::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Banking4Result.onStart: " + System.identityHashCode(this))
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Banking4Result.onResume: " + System.identityHashCode(this))
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Banking4Result.onPause: " + System.identityHashCode(this))
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Banking4Result.onStop: " + System.identityHashCode(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Banking4Result.onDestroy: " + System.identityHashCode(this))
    }

}
