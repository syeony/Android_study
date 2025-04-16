package com.ssafy.googlemap

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.ssafy.googlemap.util.PermissionChecker
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.*

private const val TAG = "PlacesActivity_싸피"
class PlacesActivity : AppCompatActivity() {

    private var mMap: GoogleMap? = null
    private var currentMarker: Marker? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentPosition: Location

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    /** permission check **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_places_location)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(readyCallback)

        /** button Event 처리 **/
        val btnSearch:Button = findViewById(R.id.btnSearch)
        btnSearch.setOnClickListener {
            showPlaceInformation(currentPosition)
        }
    }

    private val readyCallback:OnMapReadyCallback by lazy {
        object : OnMapReadyCallback {
            override fun onMapReady(p0: GoogleMap) {
                mMap = p0

                //퍼미션 요청 대화상자 (권한이 없을때) & 실행 시 초기 위치를 서울 중심부로 이동
                setDefaultLocation()

                /** permission check **/
                if (!checker.checkPermission(this@PlacesActivity, runtimePermissions)) {
                    checker.setOnGrantedListener {
                        //퍼미션 획득 성공일때
                        startLocationUpdates()
                    }
                    checker.requestPermissionLauncher.launch(runtimePermissions)
                } else { //이미 전체 권한이 있는 경우
                    startLocationUpdates()
                }
                /** permission check **/

            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (checker.checkPermission(this, runtimePermissions)) {
            startLocationUpdates()
        }
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
                currentPosition = locationList[locationList.size - 1]

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(currentPosition)
            }
        }
    }


    fun setCurrentLocation(location:Location){
        val markerTitle: String = getCurrentAddress(location)
        val markerSnippet =
            "위도: ${location.latitude.toString()}, 경도: ${location.longitude }"

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, markerSnippet)
    }

    fun setCurrentLocation(location: Location, markerTitle: String?, markerSnippet: String?) {
        currentMarker?.remove()

        val currentLatLng = LatLng(location.latitude, location.longitude)

        val markerOptions = MarkerOptions().apply{
            position(currentLatLng)
            title(markerTitle)
            snippet(markerSnippet)
            draggable(true)
        }

        currentMarker = mMap?.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f)
        mMap?.animateCamera(cameraUpdate)
    }

    fun getCurrentAddress(latLng: LatLng):String{
        val location = Location("")
        location.latitude = latLng.latitude
        location.longitude = latLng.longitude
        return getCurrentAddress(location)
    }

    fun getCurrentAddress(location: Location): String {
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
//            Toast.makeText(this, "주소 발견 불가", Toast.LENGTH_LONG).show()
            "주소 발견 불가"
        } else {
            val address = addresses[0]
            address.getAddressLine(0).toString()
        }
    }


    private fun setDefaultLocation() {
        //초기 위치를 서울로
        var location = Location("")
        location.latitude = 37.56
        location.longitude = 126.97

        val markerTitle = "위치정보 가져올 수 없음"
        val markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인 필요"

        if (checker.checkPermission(this, runtimePermissions)) {
            mFusedLocationClient.lastLocation.addOnSuccessListener { loc:Location? ->
                if(loc != null) {
                    location = loc
                    setCurrentLocation(location)
                }
            }
        }

        setCurrentLocation(location, markerTitle, markerSnippet)
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
                    Toast.makeText(this@PlacesActivity,
                        "위치 서비스가 꺼져 있어, 현재 위치를 확인할 수 없습니다.",
                        Toast.LENGTH_SHORT).show()
                }
        }
    }

    /** places 검색 추가.**/
    private var exMarker = mutableSetOf<MarkerOptions>()

    private fun showPlaceInformation(location: Location) {
        mMap?.clear() //지도 클리어
        exMarker.clear() //지역정보 마커 클리어

        Thread {
            // 실물폰으로 테스트 하면 localhost가 아니라 ip로 접근
            //터미널에서 ipconfig치고 ipv4주소 넣기
            val url =
                URL("http://192.168.32.88:8080/nearsearch" +
                        "?lat=${location.latitude}" +
                        "&lon=${location.longitude}" +
                        "&radius=500" +
                        "&type=restaurant")
            Log.d(TAG, "showPlaceInformation: ${url}")
            val connection = url.openConnection()
            BufferedReader(InputStreamReader(connection.getInputStream())).use { reader ->
                val results = Gson().fromJson(reader, Array<Item>::class.java)
                makeResults(results)
            }
        }.start()


    }

    private fun makeResults(places: Array<Item>) {
        for (place in places) {
            val latLng = LatLng(
                place.latitude, place.longitude
            )
            val markerSnippet = getCurrentAddress(latLng) //"주소 정보"
            val markerOptions = MarkerOptions().apply{
                position(latLng)
                title(place.title)
                snippet(markerSnippet)
            }

            exMarker.add(markerOptions)
        }

        //UI Thread에서 추가.
        runOnUiThread{
            exMarker.forEach {
                mMap?.addMarker(it)
            }
        }
    }

}