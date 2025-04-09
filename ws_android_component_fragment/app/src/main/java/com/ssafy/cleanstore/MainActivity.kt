package com.ssafy.cleanstore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.cleanstore.databinding.ActivityMainBinding
import com.ssafy.cleanstore.dto.Stuff
import com.ssafy.cleanstore.fragment.MainFragment
import com.ssafy.cleanstore.fragment.StoreFragment
import com.ssafy.cleanstore.fragment.StuffEditFragment
import com.ssafy.cleanstore.fragment.StuffFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 처음 실행 시 기본 프래그먼트 설정
        if (savedInstanceState == null) {
            changeFragmentView(MAIN_FRAGMENT)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.action_1 -> changeFragmentView(MAIN_FRAGMENT)
                R.id.action_2 -> changeFragmentView(STORE_FRAGMENT)
            }
            true
        }

    }

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
                // 첫번 째 프래그먼트 호출
                val fragment1 = StuffFragment.newInstance(stuff, position, actionFlag)
                transaction.replace(binding.frameLayout.id, fragment1)
                transaction.commit()
            }
            STUFF_EDIT_FRAGMENT -> {
                // 두번 째 프래그먼트 호출
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
        val DEFAULT_STUFF = Stuff("", -1)
    }


}