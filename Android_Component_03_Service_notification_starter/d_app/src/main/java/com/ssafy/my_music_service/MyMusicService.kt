package com.ssafy.my_music_service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.ssafy.my_music_service.R

private const val TAG = "MyMusicService_싸피"
class MyMusicService : Service() {

    private lateinit var mp: MediaPlayer

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        mp.stop() //음악 중지
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        mp = MediaPlayer.create(this, R.raw.jazzbyrima)
        mp.isLooping=true
        mp.start()
        return START_STICKY  // 시스템에 의해 종료되어도 다시 생성
    }
}