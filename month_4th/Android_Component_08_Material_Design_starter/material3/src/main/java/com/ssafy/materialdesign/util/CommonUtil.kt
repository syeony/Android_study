package com.ssafy.materialdesign.util

import android.app.Activity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.R

object CommonUtil {
    // 하위 컴포넌트 중에서 recursive하게 MaterialButton을 찾는다.
    fun <T : View> findViewsWithType(view: View, type: Class<T>) : List<T> {
        val views = arrayListOf<T>()
        findViewsWithType(view, type, views)
        return views
    }

    fun <T : View> findViewsWithType(view: View, type: Class<T>, views:MutableList<T>){
        if (type.isInstance(view)) {
            views.add(type.cast(view)!!)
        }

        if (view is ViewGroup) {
            val viewGroup = view
            for (i in 0 until viewGroup.childCount) {
                findViewsWithType(viewGroup.getChildAt(i), type, views)
            }
        }
    }

    fun showSnackbar(activity: Activity, menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            return false
        }
        menuItem.title?.let {
            Snackbar.make(activity.findViewById(android.R.id.content),
                it, Snackbar.LENGTH_SHORT).show()
        }
        return true
    }

    // Fragment하위에 id가 container(최상위 감싸고 있는)를 리턴. back pressed 대응
    fun getCurrentFragment(activity: FragmentActivity): Fragment {
        return activity.supportFragmentManager.findFragmentById(R.id.fragment_container)!!
    }

}
