package com.ssafy.permission

import android.Manifest
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity_싸피"
class MainActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Location Manager 획득.
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager

        // 가장 최근의 위치정보
        val location = manager.getLastKnownLocation(LocationManager.FUSED_PROVIDER) //GPS_PROVIDER

        val lat = location?.latitude  //위도
        val lon = location?.longitude //경도

        Log.d(TAG, "onCreate: 위도 : $lat , 경도 :$lon")

        //1. 권한 체크
        val coarseResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val fineResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        Log.d(TAG, "onCreate.coarseResult: $coarseResult")
        Log.d(TAG, "onCreate.fineResult: $fineResult")
    }
}