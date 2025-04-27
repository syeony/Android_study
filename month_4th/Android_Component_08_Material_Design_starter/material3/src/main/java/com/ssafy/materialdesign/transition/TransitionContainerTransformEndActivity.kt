package com.ssafy.materialdesign.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.ssafy.materialdesign.BaseActivity
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.transition.TransitionContainerTransformStartActivity.Companion.configurationHelper

class TransitionContainerTransformEndActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // Set up shared element transition.
        // android.R.id.content : Activity의 roow view.
        findViewById<View>(android.R.id.content).setTransitionName("shared_element_end_root")
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildContainerTransform(true)
        window.sharedElementReturnTransition = buildContainerTransform(false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateActivityView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        return layoutInflater.inflate(R.layout.activity_transition_container_transform_end, viewGroup, false)
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform(this, entering)
        // Use all 3 container layer colors since this transform can be configured using any fade mode
        // and some of the start views don't have a background and the end view doesn't have a
        // background.
        transform.setAllContainerColors(
            MaterialColors.getColor(findViewById(android.R.id.content), R.attr.colorSurface)
        )
        transform.addTarget(android.R.id.content)
        configurationHelper!!.configure(transform, entering)
        return transform
    }

    override fun getActionBarTitle(): String {
        return getString(R.string.cat_transition_container_transform_activity_title)
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return true
    }
}

