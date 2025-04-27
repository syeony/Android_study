package com.ssafy.materialdesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.materialdesign.util.CommonUtil
import com.ssafy.materialdesign.util.OnBackPressedHandler

class MainActivity : BaseActivity() {

    override fun onCreateActivityView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.activity_main, viewGroup, false)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, StartFragment()).commit()

        return view;
    }

    // BaseActivity에 선언된 닫기버튼. 보여주지 않도록
    override fun shouldShowDefaultActionBarCloseButton(): Boolean {
        return false
    }

    //BaseActivity에 선언된 ActionBar 보여주지 않기.
    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }

    //FragmentTransaction을 이용 화면 replace
    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    //FragmentTransaction을 이용 화면 replace
    fun pop() {
        supportFragmentManager.popBackStack()
    }

    // fragment 뒤로 가기 버튼 이벤트 대응
    override fun onBackPressed() {
        if (handleFragmentOnBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    private fun handleFragmentOnBackPressed(): Boolean {
        val currentFragment: Fragment = CommonUtil.getCurrentFragment(this)
        return (currentFragment is OnBackPressedHandler
                && (currentFragment as OnBackPressedHandler).onBackPressed())
    }

}