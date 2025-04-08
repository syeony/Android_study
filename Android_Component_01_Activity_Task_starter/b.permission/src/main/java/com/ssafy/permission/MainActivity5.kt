package com.ssafy.permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity5_싸피"
class MainActivity5 : AppCompatActivity() {

    val RUNTIME_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val PERMISSION_REQUEST_CODE = 9;

    lateinit var manager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Location Manager 획득.
        manager = getSystemService(LOCATION_SERVICE) as LocationManager

        //1. 권한 체크
        val coarseResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val fineResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        Log.d(TAG, "onCreate.coarseResult: $coarseResult")
        Log.d(TAG, "onCreate.fineResult: $fineResult")

        //권한 획득했는지 체크
        if(coarseResult == PackageManager.PERMISSION_GRANTED &&
                fineResult == PackageManager.PERMISSION_GRANTED ){
            init()
        }else{
            findViewById<TextView>(R.id.textView).text = "권한이 없습니다."

            //권한이 없을때 권한 요청창 띄우기
//            ActivityCompat.requestPermissions(this, RUNTIME_PERMISSIONS,  PERMISSION_REQUEST_CODE);
            requestPermissionLauncher.launch(RUNTIME_PERMISSIONS)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){
        Log.d(TAG, "requestPermissionLauncher: 건수 : ${it.size}")

        if(it.values.contains(false)){ //false가 있는 경우라면..
            Toast.makeText(this, "권한이 부족합니다.", Toast.LENGTH_SHORT).show()
            moveToSettings()
        }else{
            Toast.makeText(this, "모든 권한이 허가되었습니다.", Toast.LENGTH_SHORT).show()
            init()
        }

    }

    @SuppressLint("MissingPermission")
    fun init(){
        // 가장 최근의 위치정보
        val location = manager.getLastKnownLocation(LocationManager.FUSED_PROVIDER) //GPS_PROVIDER

        val lat = location?.latitude  //위도
        val lon = location?.longitude //경도

        findViewById<TextView>(R.id.textView).text = "위도 :${lat}, 경도 : ${lon} "
        Log.d(TAG, "위도 :${lat}, 경도 : ${lon} ")
    }


    private fun moveToSettings() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("권한이 필요합니다.")
        alertDialog.setMessage("설정으로 이동합니다.")
        alertDialog.setPositiveButton( "확인" ) { dialogInterface, i -> // 안드로이드 버전에 따라 다를 수 있음.
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                Uri.parse("package:" + this.getPackageName())
            )
            this.startActivity(intent)
            dialogInterface.cancel()
        }
        alertDialog.setNegativeButton( "취소" ) { dialogInterface, i ->
            Toast.makeText(this, "권한이 없어서 앱이 정상동작하지 않습니다.", Toast.LENGTH_SHORT).show() // 권한없음.
            dialogInterface.cancel()
        }
        alertDialog.show()
    }
}