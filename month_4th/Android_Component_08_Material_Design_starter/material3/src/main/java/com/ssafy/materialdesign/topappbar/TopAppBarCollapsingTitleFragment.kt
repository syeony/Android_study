package com.ssafy.materialdesign.topappbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class TopAppBarCollapsingTitleFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_topappbar_collapsing_large, viewGroup, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        return view
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }

}

