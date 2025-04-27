package com.ssafy.materialdesign.progressindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.slider.Slider
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class ProgressIndicatorDeterminateFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = layoutInflater.inflate(
            R.layout.fragment_progress_indicator_determinate, container, false )
        initialize(view)
        return view
    }

    private fun initialize(view: View) {
        val linearProgressIndicatorList: List<LinearProgressIndicator> =
            CommonUtil.findViewsWithType(view, LinearProgressIndicator::class.java)
        val circularProgressIndicatorList: List<CircularProgressIndicator> =
            CommonUtil.findViewsWithType(view, CircularProgressIndicator::class.java)

        val slider = view.findViewById<Slider>(R.id.slider)
        val showButton = view.findViewById<Button>(R.id.show_button)
        val hideButton = view.findViewById<Button>(R.id.hide_button)
        slider.addOnChangeListener(
            Slider.OnChangeListener { sliderObj: Slider?, value: Float, fromUser: Boolean ->
                for (indicator in linearProgressIndicatorList) {
                    indicator.setProgressCompat(value.toInt(), true)
                }
                for (indicator in circularProgressIndicatorList) {
                    indicator.setProgressCompat(value.toInt(), true)
                }
            })
        showButton.setOnClickListener { v: View? ->
            for (indicator in linearProgressIndicatorList) {
                indicator.show()
            }
            for (indicator in circularProgressIndicatorList) {
                indicator.show()
            }
        }
        hideButton.setOnClickListener { v: View? ->
            for (indicator in linearProgressIndicatorList) {
                indicator.hide()
            }
            for (indicator in circularProgressIndicatorList) {
                indicator.hide()
            }
        }
    }
}

