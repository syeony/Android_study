package com.ssafy.my_music_service

import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

object MusicNotification {
    const val CHANNEL_ID =
        "foreground_service_channel" // 임의의 채널 ID

    fun createNotification(context: Context): Notification {
        // 알림 클릭시 MainActivity로 이동됨
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.action = Actions.MAIN
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,FLAG_IMMUTABLE)

        // 각 버튼들에 관한Intent
        val prevIntent = Intent(context, ForegroundMusicService::class.java)
        prevIntent.action = Actions.PREV
        val prevPendingIntent = PendingIntent.getService(context, 0, prevIntent, FLAG_IMMUTABLE)

        val playIntent = Intent(context, ForegroundMusicService::class.java)
        playIntent.action = Actions.PLAY
        val playPendingIntent = PendingIntent.getService(context, 0, playIntent, FLAG_IMMUTABLE)

        val nextIntent = Intent(context, ForegroundMusicService::class.java)
        nextIntent.action = Actions.STOP
        val nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, FLAG_IMMUTABLE)

        // 알림
        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle("Music Player")
                .setContentText("My Music").setSmallIcon(R.drawable.ic_baseline_album_24)
                .setOngoing(true) // true 일경우 알림 리스트에서 클릭하거나 좌우로 드래그해도 사라지지 않음
                // 14부터 ongoing 변경 :  https://developer.android.com/about/versions/14/behavior-changes-all?hl=ko#non-dismissable-notifications
                .addAction(
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_previous,
                        "Prev",
                        prevPendingIntent
                    )
                ).addAction(
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_play,
                        "Play",
                        playPendingIntent
                    )
                ).addAction(
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_next,
                        "STOP",
                        nextPendingIntent
                    )
                ).setContentIntent(pendingIntent)
                .build()

        return notification
    }
}




