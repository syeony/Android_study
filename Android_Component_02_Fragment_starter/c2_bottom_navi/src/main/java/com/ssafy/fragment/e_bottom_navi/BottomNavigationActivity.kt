package com.ssafy.fragment.e_bottom_navi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ssafy.fragment.e_bottom_navi.databinding.ActivityBottomNavigationBinding
import com.ssafy.fragment.e_bottom_navi.fragment.BottomFragment1
import com.ssafy.fragment.e_bottom_navi.fragment.BottomFragment2
import com.ssafy.fragment.e_bottom_navi.fragment.BottomFragment3
import com.ssafy.fragment.e_bottom_navi.fragment.BottomFragment4

class BottomNavigationActivity:AppCompatActivity() {

    private lateinit var binding : ActivityBottomNavigationBinding

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.bottomNavigation.setOnItemSelectedListener {
//            val transaction = supportFragmentManager.beginTransaction()
//            when(it.itemId){
//                R.id.action_1 -> transaction.replace(R.id.frame_layout, BottomFragment1())
//                R.id.action_2 -> transaction.replace(R.id.frame_layout, BottomFragment2())
//                R.id.action_3 -> transaction.replace(R.id.frame_layout, BottomFragment3())
//                R.id.action_4 -> transaction.replace(R.id.frame_layout, BottomFragment4())
//            }
//            transaction.commit()
//
//            true
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_1 -> changeFragmentView(FIRST_FRAGMENT)
                R.id.action_2 -> changeFragmentView(SECOND_FRAGMENT)
                R.id.action_3 -> changeFragmentView(THIRD_FRAGMENT)
                R.id.action_4 -> changeFragmentView(FOURTH_FRAGMENT)
            }
            true
        }

        changeFragmentView(FIRST_FRAGMENT)
    }


    //FragmentTransaction을 이용 화면 replace
    fun changeFragmentView(fragment: Int, param:String = "default Value") {
        val transaction = supportFragmentManager.beginTransaction()
        var goto: Fragment
        when (fragment) {
            FIRST_FRAGMENT -> {
                // 첫번 째 프래그먼트 호출
                goto = BottomFragment1()
            }
            SECOND_FRAGMENT -> {
                // 두번 째 프래그먼트 호출
                goto = BottomFragment2()
            }
            THIRD_FRAGMENT -> {
                goto = BottomFragment3()
            }
            FOURTH_FRAGMENT -> {
                goto = BottomFragment4()
            }
            else -> goto = BottomFragment1()
        }
        transaction.replace(R.id.frame_layout, goto)
        transaction.commit()
    }

    companion object {
        const val FIRST_FRAGMENT = 1
        const val SECOND_FRAGMENT = 2
        const val THIRD_FRAGMENT = 3
        const val FOURTH_FRAGMENT = 4
    }
}