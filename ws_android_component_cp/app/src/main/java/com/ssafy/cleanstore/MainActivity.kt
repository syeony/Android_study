package com.ssafy.cleanstore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.cleanstore.databinding.ActivityMainBinding
import com.ssafy.cleanstore.dto.Stuff
import com.ssafy.cleanstore.fragment.MainFragment
import com.ssafy.cleanstore.fragment.StoreFragment
import com.ssafy.cleanstore.fragment.StuffEditFragment
import com.ssafy.cleanstore.fragment.StuffFragment
import com.ssafy.cleanstore.service.BoundService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // BoundService 관련 멤버
    private lateinit var serviceIntent: Intent
    private var boundService: BoundService? = null
    private var isBound = false

    // ServiceConnection 객체
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
            isBound = true

            // Service 연결되었을 때 DB에서 목록 조회해서 StuffFragment에 전달
            supportFragmentManager.findFragmentById(binding.frameLayout.id)?.let { fragment ->
                if (fragment is StuffFragment) {
                    fragment.refreshList(boundService?.getList() ?: emptyList())
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            boundService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceIntent = Intent(this, BoundService::class.java)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.action_1 -> changeFragmentView(MAIN_FRAGMENT)
                R.id.action_2 -> changeFragmentView(STORE_FRAGMENT)
            }
            true
        }

        changeFragmentView(MAIN_FRAGMENT)
    }

    //서비스가 화면에 표시되면 ServiceConnection의 onServiceConnected()콜백이 호출
    //그래서 boundService 객체를 사용할 수 있게 됨
    override fun onStart() {
        super.onStart()
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }

    //화면에서 사라질때 unbindService()를 호출하며 서비스와의 연결 종료
    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    fun getBoundService(): BoundService? = boundService

    // MainActivity.kt 안에 추가
    fun toggleBottomNavigationView(isVisible: Boolean) {
        if (isVisible) {
            binding.bottomNavigation.visibility = View.VISIBLE
        } else {
            binding.bottomNavigation.visibility = View.GONE
        }
    }

    //FragmentTransaction 을 이용 화면 replace
    fun changeFragmentView(fragment: Int, stuff: Stuff = DEFAULT_STUFF, position : Int = -1,
                           actionFlag:Int = -1) {
        val transaction = supportFragmentManager.beginTransaction()
        when (fragment) {
            MAIN_FRAGMENT -> {
                transaction.replace(binding.frameLayout.id, MainFragment()).commit()
            }
            STORE_FRAGMENT -> {
                transaction.replace(binding.frameLayout.id, StoreFragment()).commit()
            }
            STUFF_FRAGMENT -> {
                val fragment1 = StuffFragment.newInstance(stuff, position, actionFlag)
                transaction.replace(binding.frameLayout.id, fragment1)
                transaction.commit()
            }
            STUFF_EDIT_FRAGMENT -> {
                val fragment2 = StuffEditFragment.newInstance(stuff, position, actionFlag)
                transaction.replace(binding.frameLayout.id, fragment2)
                transaction.commit()
            }
        }
    }

    companion object {
        const val MAIN_FRAGMENT = 1
        const val STORE_FRAGMENT = 2
        const val STUFF_FRAGMENT = 3
        const val STUFF_EDIT_FRAGMENT = 4
        val DEFAULT_STUFF = Stuff(-1,"", -1)
    }
}

