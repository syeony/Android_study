package com.ssafy.my_music_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log

private const val TAG = "ForeMusicService_싸피"
class ForegroundMusicService : Service() {
    lateinit var mp: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer.create(this,R.raw.jazzbyrima)
        Log.d(TAG, "onCreate()")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d( TAG, "Action Received = ${intent?.action}" )
        when (intent?.action) {
            Actions.START_FOREGROUND -> {
                Log.d(TAG, "Start Foreground 인텐트를 받음")
                if( !mp.isPlaying ){
                    mp.isLooping = true
                    mp.start()
                    startForegroundService()
                }
            }
            Actions.STOP_FOREGROUND -> {
                Log.d(TAG, "Stop Foreground 인텐트를 받음")
                if( mp.isPlaying ) {
                    stopForegroundService()
                    mp.stop() //음악 중지
                }
            }
            Actions.PLAY ->{
                Log.d(TAG, "start music from notification : ${mp.isPlaying}")
                if( !mp.isPlaying ){
                    Log.d(TAG, "start music from notification")
                    mp.start()
                }
            }
            Actions.STOP ->{
                Log.d(TAG, "stop music from notification")
                if( mp.isPlaying ) mp.pause() //음악 중지
            }
        }; return START_STICKY
    }

    private fun startForegroundService() {

        // Oreo 부터는 Notification Channel을 만들어야 함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                MusicNotification.CHANNEL_ID,
                "Music Player Channel", // 채널표시명
//                NotificationManager.IMPORTANCE_DEFAULT
                NotificationManager.IMPORTANCE_LOW //소리없는 알림추가
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }


        val notification = MusicNotification.createNotification(this)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stopForegroundService() {
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        // bindservice가 아니므로 null
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    companion object {
        const val NOTIFICATION_ID = 20
    }
}