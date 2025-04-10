package com.ssafy.my_music_service

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.my_music_service.databinding.ActivityForegroundBinding
import com.ssafy.my_music_service.util.PermissionChecker

class ForegroundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForegroundBinding

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    /** permission check **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForegroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()

    }

    private fun checkPermission(){

        /** permission check **/
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener{
                initEvent()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            initEvent()
        }
        /** permission check **/
    }

    fun initEvent(){

        binding.btnStart.setOnClickListener {
            val intent = Intent(this,ForegroundMusicService::class.java)
            intent.action = Actions.START_FOREGROUND
            startService(intent)
            it.isClickable = false
            binding.btnStop.isClickable = true
        }

        binding.btnStop.setOnClickListener {
            val intent = Intent(this, ForegroundMusicService::class.java )
            intent.action = Actions.STOP_FOREGROUND
            startService(intent)
            binding.btnStart.isClickable = true
            it.isClickable = false
        }
    }
}