package com.ssafy.materialdesign.fab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

open class FabFragmentShrink : BaseFragment() {

    private var fabsShown = true
    private var fabsExpanded = true

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val root: View = layoutInflater.inflate(R.layout.fragment_fab_shrink, viewGroup, false)
        val content = root.findViewById<ViewGroup>(R.id.content)
        View.inflate(context, getExtendedFabContent(), content)
        val extendedFabs: List<ExtendedFloatingActionButton> =
            CommonUtil.findViewsWithType(root, ExtendedFloatingActionButton::class.java)
        for (extendedFab in extendedFabs) {
            extendedFab.setOnClickListener { v: View ->
                Snackbar.make(v, R.string.cat_extended_fab_clicked, Snackbar.LENGTH_SHORT).show()
            }
        }
        val showHideFabs = root.findViewById<Button>(R.id.show_hide_fabs)
        showHideFabs.setOnClickListener { v: View? ->
            for (extendedFab in extendedFabs) {
                if (fabsShown) {
                    extendedFab.hide()
                    showHideFabs.setText(R.string.show_fabs_label)
                } else {
                    extendedFab.show()
                    showHideFabs.setText(R.string.hide_fabs_label)
                }
            }
            fabsShown = !fabsShown
        }
        val collapseExpandFabs = root.findViewById<Button>(R.id.collapse_expand_fabs)
        collapseExpandFabs.setOnClickListener { v: View? ->
            for (extendedFab in extendedFabs) {
                if (fabsExpanded) {
                    extendedFab.shrink()
                    collapseExpandFabs.setText(R.string.extend_fabs_label)
                } else {
                    extendedFab.extend()
                    collapseExpandFabs.setText(R.string.shrink_fabs_label)
                }
            }
            fabsExpanded = !fabsExpanded
        }
        return root
    }

    @LayoutRes
    protected open fun getExtendedFabContent(): Int {
        return R.layout.m3_extended_fabs
    }
}
