package com.ssafy.music.myservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.MediaStore.Audio.Media
import android.util.Log

private const val TAG = "MyMusicService_싸피"
class MyMusicService : Service() {

    private lateinit var mp3:MediaPlayer

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        // mp3 플레이.
        mp3 = MediaPlayer.create(this, R.raw.jazzbyrima)
        mp3.isLooping = true
        mp3.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mp3.stop()
        Log.d(TAG, "onDestroy: ")
    }
}