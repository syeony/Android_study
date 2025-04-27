package com.ssafy.materialdesign.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.StringRes
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class SliderFragment : BaseFragment() {


    private val touchListener: Slider.OnSliderTouchListener =
        object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                showSnackbar(slider, R.string.cat_slider_start_touch_description)
            }

            override fun onStopTrackingTouch(slider: Slider) {
                showSnackbar(slider, R.string.cat_slider_stop_touch_description)
            }
        }

    private val rangeSliderTouchListener: RangeSlider.OnSliderTouchListener =
        object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                showSnackbar(slider, R.string.cat_slider_start_touch_description)
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                showSnackbar(slider, R.string.cat_slider_stop_touch_description)
            }
        }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_slider, viewGroup, false )
        val slider = view.findViewById<Slider>(R.id.slider)
        slider.addOnSliderTouchListener(touchListener)

        val rangeSlider = view.findViewById<RangeSlider>(R.id.range_slider)
        rangeSlider.addOnSliderTouchListener(rangeSliderTouchListener)

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener { v: View? ->
            slider.value = slider.valueTo
        }
        return view
    }

    private fun showSnackbar(view: View, @StringRes messageRes: Int) {
        Snackbar.make(view, messageRes, Snackbar.LENGTH_SHORT).show()
    }

}
