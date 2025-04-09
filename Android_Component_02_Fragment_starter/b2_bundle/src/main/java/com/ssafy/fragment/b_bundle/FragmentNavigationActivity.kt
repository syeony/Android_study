package com.ssafy.fragment.b_bundle

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "FragmentNavigation_싸피"

class FragmentNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_navigation)

        val defaultParam = "Activity에서 호출합니다."
        findViewById<View>(R.id.btn1).setOnClickListener { changeFragmentView(FIRST_FRAGMENT, defaultParam) }
        findViewById<View>(R.id.btn2).setOnClickListener { changeFragmentView(SECOND_FRAGMENT, defaultParam) }

        changeFragmentView(FIRST_FRAGMENT, defaultParam)
    }

    //FragmentTransaction을 이용 화면 replace
    fun changeFragmentView(fragment: Int, param:String) {
        val transaction = supportFragmentManager.beginTransaction()

        when (fragment) {
            FIRST_FRAGMENT -> {
                // 첫번 째 프래그먼트 호출
                val fragment1 = Fragment1.newInstance(param)
                transaction.replace(R.id.fragment_container, fragment1)
            }
            SECOND_FRAGMENT -> {
                // 두번 째 프래그먼트 호출
                val fragment2 = Fragment2.newInstance(param)
                transaction.replace(R.id.fragment_container, fragment2)
            }
        }
        transaction.commit()
    }



    companion object {
        const val FIRST_FRAGMENT = 1
        const val SECOND_FRAGMENT = 2
    }
}