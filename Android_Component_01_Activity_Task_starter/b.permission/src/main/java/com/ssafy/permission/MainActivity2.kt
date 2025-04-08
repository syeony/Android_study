package com.ssafy.permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


private const val TAG = "MainActivity2_싸피"
class MainActivity2 : AppCompatActivity() {

    lateinit var manager:LocationManager
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
}