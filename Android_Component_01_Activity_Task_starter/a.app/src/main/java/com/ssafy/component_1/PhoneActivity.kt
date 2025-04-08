package com.ssafy.component_1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.ssafy.component_1.databinding.ActivityPhoneBinding


class PhoneActivity : AppCompatActivity() {

    lateinit var binding : ActivityPhoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 전화 다이얼걸기 - 통화연결까지는 하지 않음
        binding.dialBtn.setOnClickListener {
            // 어디에 전화를 걸건지 text 정보 받기
            val input = binding.phoneEt.text.toString()
            // Uri 를 이용하여 정보 저장
            val uri = Uri.parse("tel:${input}")
            // 전화 정보 설정 - ACTION_DIAL & 이동
            startActivity(Intent(Intent.ACTION_DIAL, uri))
        }

        // 전화걸기 - 통화연결까지
        binding.callBtn.setOnClickListener {
            // 어디에 전화를 걸건지 text 정보 받기
            val input = binding.phoneEt.text.toString()
            val permissionListener = object : PermissionListener {
                // 권한 얻기에 성공했을 때 동작 처리
                override fun onPermissionGranted() {

                    val uri = Uri.parse("tel:${input}")
                    // 전화 정보 설정 - ACTION_CALL & 이동
                    startActivity(Intent(Intent.ACTION_CALL, uri))
                }
                // 권한 얻기에 실패했을 때 동작 처리
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@PhoneActivity, "전화 연결 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("[설정] 에서 권한을 열어줘야 전화 연결이 가능합니다.")
                    // 필요한 권한 설정
                .setPermissions(android.Manifest.permission.CALL_PHONE)
                .check()
        }
    }
}