package com.ssafy.materialdesign.fab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

open class FabFragment : BaseFragment() {

    private var fabsShown = true

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_fab, container, false /* attachToRoot */)
        val content = view.findViewById<ViewGroup>(R.id.content)
        View.inflate(context, getFabsContent(), content)
        View.inflate(context, getThemeFabLayoutResId(), content)
        val fabs: List<FloatingActionButton> = CommonUtil.findViewsWithType(view,FloatingActionButton::class.java)
        for (fab in fabs) {
            fab.setOnClickListener { v: View ->
                Snackbar.make(v, R.string.cat_fab_clicked, Snackbar.LENGTH_SHORT).show()
            }
        }
        val showHideFabs = view.findViewById<Button>(R.id.show_hide_fabs)
        showHideFabs.setOnClickListener { v: View ->
            for (fab in fabs) {
                if (fabsShown) {
                    fab.hide()
                    showHideFabs.setText(R.string.show_fabs_label)
                } else {
                    fab.show()
                    showHideFabs.setText(R.string.hide_fabs_label)
                }
            }
            fabsShown = !fabsShown
        }
        val spinFabs = view.findViewById<Button>(R.id.rotate_fabs)
        spinFabs.setOnClickListener { v: View ->
            if (!fabsShown) {
                return@setOnClickListener
            }
            for (fab in fabs) {
                fab.rotation = 0f
                ViewCompat.animate(fab)
                    .rotation(360f)
                    .withLayer()
                    .setDuration(1000)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
            }
        }
        return view
    }

    protected open fun getFabsContent(): Int {
        return R.layout.m3_fabs
    }

    protected open fun getThemeFabLayoutResId(): Int {
        return R.layout.m3_theme_fab
    }
}
