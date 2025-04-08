package com.ssafy.component_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ssafy.component_1.databinding.ActivityDetailBinding

private const val TAG = "DetailActivity_싸피"
class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding
    private var value=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: ")

        if(savedInstanceState != null){
            value=savedInstanceState.getInt("count")
        }else{
            value=0
        }
        binding.textView.text=value.toString()

        binding.plus.setOnClickListener {
            value++
            binding.textView.text=value.toString()
        }

        binding.minus.setOnClickListener {
            value--
            binding.textView.text=value.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
        outState.putInt("count",value)
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