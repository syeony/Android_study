package com.ssafy.googlemap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ssafy.googlemap.util.PermissionChecker
import java.io.IOException
import java.util.Locale

private const val TAG = "MyLocationActivity_싸피"
class MyLocationActivity : AppCompatActivity() {

    private var mMap: GoogleMap? = null
    private var currentMarker: Marker? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    /** permission check **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_my_location)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(readyCallback)

        //fragment라면, MapView를 사용하거나 childFragmentManager를 사용
        // MapView를 쓴다면 activity의 lifecycle에 따라 mapview의 onCreate, onResume을 직접 호출해줘야 한다.
//        val fragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        fragment.getMapAsync(onSmallMapReadyCallback)
    }


    private val readyCallback:OnMapReadyCallback by lazy{
        object: OnMapReadyCallback {
            override fun onMapReady(p0: GoogleMap) {
                mMap = p0

                //퍼미션 요청 대화상자 (권한이 없을때) & 실행 시 초기 위치를 서울 중심부로 이동
                setDefaultLocation()

                /** permission check **/
                if (!checker.checkPermission(this@MyLocationActivity, runtimePermissions)) {
                    checker.setOnGrantedListener {
                        //퍼미션 획득 성공일때
                        startLocationUpdates()
                    }
                    checker.requestPermissionLauncher.launch(runtimePermissions)
                } else { //이미 전체 권한이 있는 경우
                    startLocationUpdates()
                }
                /** permission check **/

                //클릭시 맵 위치이동
                mMap?.setOnMapClickListener {
                    val location=Location("")
                    location.latitude = it.latitude
                    location.longitude = it.longitude

                    setCurrentLocation(location)
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        mFusedLocationClient.removeLocationUpdates(locationCallback)

    }

    private fun startLocationUpdates() {
        // 위치서비스 활성화 여부 check
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting()
        } else {
            if (checker.checkPermission(this, runtimePermissions)) {

                mMap?.isMyLocationEnabled = true
                mMap?.uiSettings?.isZoomControlsEnabled = true

                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,null)
            }
        }
    }

    private val locationRequest: LocationRequest by lazy{
        LocationRequest.create().apply {
            interval = 1000   // 1초
            fastestInterval = 500  // 0.5초
            smallestDisplacement = 10.0f   //10m
        }
    }

    //위치정보 요청시 호출
    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                val location = locationList[locationList.size - 1]

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location)
            }
        }
    }

    fun setCurrentLocation(location: Location){
        val markerTitle: String = getCurrentAddress(location)
        val markerSnippet = "위도: ${location.latitude.toString()}, 경도: ${location.longitude }"

//          Log.d(TAG, "onLocationResult: 위도: ${location.latitude.toString()}, 경도: ${location.longitude}")

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, markerSnippet)
    }

    fun setCurrentLocation(location: Location, markerTitle: String?, markerSnippet: String?) {
        currentMarker?.remove()

        /** 마커를 새로 만듦 **/
        val currentLatLng = LatLng(location.latitude, location.longitude)

        //val marker = resources.getDrawable(R.drawable.location_icon,theme).toBitmap(150,150)
        val marker = ResourcesCompat.getDrawable(resources,R.drawable.location_icon, theme)!!.toBitmap(150,150)
        //마커에 대한 정보
        val markerOptions = MarkerOptions().apply{
            position(currentLatLng)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
            icon(BitmapDescriptorFactory.fromBitmap(marker)) //이쁘게 만든거
            //icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)) //빨간색 화살표 아이콘
        }
        Log.d(TAG, "setCurrentLocation: $currentLatLng")
        /** 마커를 새로 만듦 **/

        //맵에다가 마커 추가
        currentMarker = mMap?.addMarker(markerOptions)

        //카메라 업데이트
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f)
        mMap?.moveCamera(cameraUpdate)
        //animateCamera사용하면 초기위치(서울)에서 애니메이션이 슉 넘어감

    }

    fun getCurrentAddress(location:Location): String {
        //지오코더: GPS를 주소로 변환
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?
        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }

        return if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(this, "주소 발견 불가", Toast.LENGTH_LONG).show()
            "주소 발견 불가"
        } else {
            val address = addresses[0]
            address.getAddressLine(0).toString()
        }
    }

    private fun setDefaultLocation() {

        //lastLocation가져오기
        if(checker.checkPermission(this,runtimePermissions)){
            mFusedLocationClient.lastLocation.addOnSuccessListener {
                setCurrentLocation(it)
            }
        }else{
            //초기 위치를 서울로
            val location = Location("")
            location.latitude = 37.56
            location.longitude = 126.97

            val markerTitle = "위치정보 가져올 수 없음"
            val markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인 필요"

            setCurrentLocation(location, markerTitle, markerSnippet)
        }
    }



    /** 권한 관련 **/
    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }


    /******** 위치서비스 활성화 여부 check *********/
    private val GPS_ENABLE_REQUEST_CODE = 2001
    private var needRequest = false

    private fun showDialogForLocationServiceSetting() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { _, _ ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }
        builder.setNegativeButton("취소"
        ) { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                //사용자가 GPS를 켰는지 검사함
                if (checkLocationServicesStatus()) {
                    needRequest = true
                    return
                }else{
                    Toast.makeText(this@MyLocationActivity,
                        "위치 서비스가 꺼져 있어, 현재 위치를 확인할 수 없습니다.",
                        Toast.LENGTH_SHORT).show()
                }
        }
    }
}

