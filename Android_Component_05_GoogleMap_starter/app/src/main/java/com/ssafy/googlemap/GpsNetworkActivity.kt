package com.ssafy.googlemap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.googlemap.databinding.ActivityGpsNetworkBinding
import com.ssafy.googlemap.util.PermissionChecker

private const val TAG = "GpsNetworkActivity_싸피"
class GpsNetworkActivity : AppCompatActivity() {

    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private lateinit var binding: ActivityGpsNetworkBinding

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    /** permission check **/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGpsNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* permission check */
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener{
                //퍼미션 획득 성공일때
                initView()
            }
            checker.requestPermissionLauncher.launch(runtimePermissions)
        } else { //이미 전체 권한이 있는 경우
            initView()
        }
        /* permission check */
    }

    private fun initView() {
        getProviders()
        setLastLocation()
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun setLastLocation() {
        //GPS provider
        var lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            binding.tvGpsLatitude.text = ":: ${lastKnownLocation.latitude}"
            binding.tvGpsLongitude.text = ":: ${lastKnownLocation.longitude}"
            Log.d(TAG, "latitude=${lastKnownLocation.latitude}, longitude=${lastKnownLocation.longitude}")
        }
        //network provider
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (lastKnownLocation != null) {
            binding.tvNetworkLatitude.text = ":: ${lastKnownLocation.latitude}"
            binding.tvNetworkLongitude.text = ":: ${lastKnownLocation.longitude}"
            Log.d(TAG, "latitude=${lastKnownLocation.latitude}, longitude=${lastKnownLocation.longitude}")
        }
        //passive provider
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        if (lastKnownLocation != null) {
            binding.tvPassiveLatitude.text = ":: ${lastKnownLocation.latitude}"
            binding.tvPassiveLongitude.text = ":: ${lastKnownLocation.longitude}"
            Log.d(TAG, "latitude=${lastKnownLocation.latitude}, longitude=${lastKnownLocation.longitude}")
        }
        //fused provider
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER)
        if (lastKnownLocation != null) {
            binding.tvFusedLatitude.text = ":: ${lastKnownLocation.latitude}"
            binding.tvFusedLongitude.text = ":: ${lastKnownLocation.longitude}"
            Log.d(TAG, "latitude=${lastKnownLocation.latitude}, longitude=${lastKnownLocation.longitude}")
        }
    }

    @SuppressLint("MissingPermission")
    private fun getProviders(){
        val listProviders = locationManager.allProviders as MutableList<String>
        val isEnable = BooleanArray(4)
        for (provider in listProviders) {
            when ( provider ) {
                LocationManager.GPS_PROVIDER -> {
                    isEnable[0] = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    binding.tvGpsEnable.text = ": " + isEnable[0].toString()
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f, listener)
                    Log.d(TAG, provider + '/' + isEnable[0].toString())
                }
                LocationManager.NETWORK_PROVIDER -> {
                    isEnable[1] = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                    binding.tvNetworkEnable.text = ": " + isEnable[1].toString()
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0f, listener)
                    Log.d(TAG, provider + '/' + isEnable[1].toString())
                }
                LocationManager.PASSIVE_PROVIDER -> {
                    isEnable[2] = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)
                    binding.tvPassiveEnable.text = ": " + isEnable[2].toString()
                    locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0,0f, listener)
                    Log.d(TAG, provider + '/' + isEnable[2].toString())
                }
                LocationManager.FUSED_PROVIDER -> {
                    isEnable[3] = locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER)
                    binding.tvFusedEnable.text = ": " + isEnable[3].toString()
                    locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER,0,0f, listener)
                    Log.d(TAG, provider + '/' + isEnable[3].toString())
                }
            }
        }
    }

    private val listener = object : LocationListener {
        //위치가 변경될때 호출될 method
        @SuppressLint("SetTextI18n")
        override fun onLocationChanged(location: Location) {
            when(location.provider) {
                LocationManager.GPS_PROVIDER -> {
                    binding.tvGpsLatitude.text = ": ${location.latitude}"
                    binding.tvGpsLongitude.text = ": ${location.longitude}"
                    Log.d("$TAG GPS : ", "${location.latitude}/${location.longitude}")
                }
                LocationManager.NETWORK_PROVIDER -> {
                    binding.tvNetworkLatitude.text = ": ${location.latitude}"
                    binding.tvNetworkLongitude.text = ": ${location.longitude}"
                    Log.d("$TAG NETWORK : ", "${location.latitude}/${location.longitude}")
                }
                LocationManager.PASSIVE_PROVIDER -> {
                    binding.tvPassiveLatitude.text = ": ${location.latitude}"
                    binding.tvPassiveLongitude.text = ": ${location.longitude}"
                    Log.d("$TAG PASSIVE : ", "${location.latitude}/${location.longitude}")
                }
                LocationManager.FUSED_PROVIDER -> {
                    binding.tvFusedLatitude.text = ": ${location.latitude}"
                    binding.tvFusedLatitude.text = ": ${location.longitude}"
                    Log.d("$TAG FUSED : ", "${location.latitude}/${location.longitude}")
                }
            }
        }

        override fun onProviderEnabled(provider: String) {}

    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if(isPermitted()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, listener)
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0f, listener)
            locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER, 0, 0f, listener)

        }
    }

    override fun onPause() {
        super.onPause()
        if (isPermitted()) {
            locationManager.removeUpdates(listener)
        }
    }

    private fun isPermitted():Boolean{
        return checker.checkPermission(this,runtimePermissions)

    }
}