package com.ssafy.my_music_service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ssafy.my_music_service.databinding.ActivityPictureNotificationBinding
import com.ssafy.my_music_service.util.PermissionChecker

class PictureNotification:AppCompatActivity (){

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    /** permission check **/

    lateinit var binding:ActivityPictureNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()

        binding.buttonNoti.setOnClickListener{
            createNotification();
        }
    }

    private fun checkPermission(){

        /** permission check **/
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener{
            }
            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
        }
        /** permission check **/
    }

    @SuppressLint("MissingPermission")
    fun createNotification(){
        // 채널은 한번생성 후 importance를 바꿔도 잘 안됨.
        // channelId를 새로 생성함.
        // headup notification을 위해서 high로 세팅
        val channelId = "$packageName-$javaClass-headup"
        val title = "Picture Notification Headup"
        val content = "This is Picture notification"

        // Oreo 부터는 Notification Channel을 만들어야 함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                title,
                NotificationManager.IMPORTANCE_HIGH // headup notification을 위해서 high로 세팅
            )
            val manager = applicationContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }

        val intent = Intent(applicationContext, PictureNotification::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0,
            intent, PendingIntent.FLAG_IMMUTABLE)

        val style = NotificationCompat.BigPictureStyle()
        style.bigPicture( BitmapFactory.decodeResource(resources, R.drawable.grand) )
//        style.bigLargeIcon(null)

        val builder = NotificationCompat.Builder(this, channelId)
        builder.setStyle(style)
        builder.setSmallIcon(R.drawable.ic_baseline_add_comment_24)
//        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.grand))
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.priority = NotificationCompat.PRIORITY_HIGH      // head up message
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(hashCode(), builder.build())
    }

}