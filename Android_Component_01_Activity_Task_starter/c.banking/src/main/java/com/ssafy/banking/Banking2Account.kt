package com.ssafy.banking

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.banking.databinding.ActivityBanking2Binding
import java.util.*

private const val TAG = "Banking2Account_싸피"
class Banking2Account : AppCompatActivity() {
    private lateinit var binding:ActivityBanking2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBanking2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTime.text = Date().toString()
        Log.d(TAG, "Banking2Account.onCreate: ")

        binding.btnOtherApp.setOnClickListener {
            val intent = Intent()
            val cname = ComponentName("com.ssafy.component_1", "com.ssafy.component_1.MainActivity")
            intent.component = cname
            startActivity(intent)
        }

        binding.btnWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(intent)
        }

        binding.btnTransfer.setOnClickListener {
            val intent = Intent(this, Banking3Transfer::class.java)
            startActivity(intent)
        }

        binding.btnAccountInfo2.setOnClickListener {
            val intent = Intent(this, Banking2Account::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Banking2Account.onStart: " + System.identityHashCode(this))
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Banking2Account.onResume: " + System.identityHashCode(this))
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Banking2Account.onPause: " + System.identityHashCode(this))
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Banking2Account.onStop: " + System.identityHashCode(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Banking2Account.onDestroy: " + System.identityHashCode(this))
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "Banking2Account.onNewIntent: " + System.identityHashCode(this))
        binding.tvTime.text = Date().toString()
    }

}