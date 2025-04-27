package com.ssafy.materialdesign.fab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.MainActivity
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class FabFragmentCollapsing : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val root = layoutInflater.inflate(getExtendedFabContent(), viewGroup, false /* attachToRoot */)
        val toolbar = root.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener{
            (requireActivity() as MainActivity).pop()
        }

        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)

        val extendedFabs: List<ExtendedFloatingActionButton> = CommonUtil.findViewsWithType(root, ExtendedFloatingActionButton::class.java)
        for (extendedFab in extendedFabs) {
            extendedFab.setOnClickListener { v: View ->
                Snackbar.make(v, R.string.cat_extended_fab_clicked, Snackbar.LENGTH_SHORT).show()
            }
        }
        return root
    }

    // 기본 action bar 안보이게.
    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }

    private fun getExtendedFabContent(): Int {
        return R.layout.fragment_fab_collapsing
    }
}

