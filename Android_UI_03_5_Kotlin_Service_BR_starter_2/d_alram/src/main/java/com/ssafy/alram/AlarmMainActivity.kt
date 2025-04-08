package com.ssafy.alram

import android.app.KeyguardManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.alram.databinding.ActivityAlarmMainBinding


class AlarmMainActivity : AppCompatActivity() {
    private lateinit var player: MediaPlayer
    private lateinit var binding: ActivityAlarmMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 화면 잠김 대응. Lockscreen위에 알람 띄우기.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        }else{
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            )
        }

        binding = ActivityAlarmMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        음악 플레이어 실행.
        player = MediaPlayer.create(this, R.raw.sound1)
        player.start()

        binding.btnAlarmOff.setOnClickListener{
            releasePlayer()
            finish()
        }
    }

    private fun releasePlayer() {
        if (player.isPlaying) {
            player.stop()
            player.release()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

}



