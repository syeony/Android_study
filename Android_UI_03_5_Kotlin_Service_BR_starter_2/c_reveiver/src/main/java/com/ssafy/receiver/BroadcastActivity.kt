package com.ssafy.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.annotation.RequiresApi

private const val TAG = "BroadcastActivity_싸피"

class BroadcastActivity : AppCompatActivity() {

    lateinit var receiver:BroadcastReceiver
    lateinit var brBattery: BatteryReceiver

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)
        setTitle("BroadcastActivity")

        /***** Screen On/Off *******/
        val intentFilter2 = IntentFilter(Intent.ACTION_SCREEN_OFF)
        intentFilter2.addAction(Intent.ACTION_SCREEN_ON)
        receiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                Log.d(TAG, "receive : $action")

                when (action) {
                    Intent.ACTION_SCREEN_ON -> {
                        // do something
                        Log.d(TAG,"ACTION_SCREEN_ON");
                    }
                    Intent.ACTION_SCREEN_OFF -> {
                        // do something
                        Log.d(TAG,"ACTION_SCREEN_OFF");
                    }
                }
            }
        }
        registerReceiver(receiver, intentFilter2);
        /***** Screen On/Off *******/


        /***** low receiver. emulator에서 battery수치 15이하로 조정. *******/
        brBattery = BatteryReceiver()
        val filterBattery = IntentFilter(Intent.ACTION_BATTERY_LOW)
        registerReceiver(brBattery, filterBattery)


    }


    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(receiver)
        unregisterReceiver(brBattery)
    }
}