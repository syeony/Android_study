package com.ssafy.materialdesign.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class SliderLabelFragment : BaseFragment() {


    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_slider_label_behavior, viewGroup, false)
        setUpSlider(view, R.id.switch_button_1, R.id.slider_1)
        setUpRangeSlider(view, R.id.switch_button_2, R.id.slider_2)
        setUpRangeSlider(view, R.id.switch_button_3, R.id.slider_3)
        setUpRangeSlider(view, R.id.switch_button_4, R.id.slider_4)
        return view
    }

    private fun setUpSlider(view: View, @IdRes switchId: Int, @IdRes sliderId: Int) {
        val slider = view.findViewById<Slider>(sliderId)
        val switchButton = view.findViewById<SwitchCompat>(switchId)
        switchButton.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            slider.isEnabled = isChecked
        }
        switchButton.isChecked = true
    }

    private fun setUpRangeSlider(view: View, @IdRes switchId: Int, @IdRes sliderId: Int) {
        val slider = view.findViewById<RangeSlider>(sliderId)
        val switchButton = view.findViewById<SwitchCompat>(switchId)
        switchButton.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            slider.isEnabled = isChecked
        }
        switchButton.isChecked = true
    }


}
