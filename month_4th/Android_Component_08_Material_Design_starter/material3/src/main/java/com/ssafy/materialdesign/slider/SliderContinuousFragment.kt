package com.ssafy.materialdesign.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class SliderContinuousFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_slider_continuous, viewGroup, false)
        val sliderOne = view.findViewById<Slider>(R.id.slider_1)
        val sliderTwo = view.findViewById<Slider>(R.id.slider_2)
        val sliderThree = view.findViewById<Slider>(R.id.slider_3)
        val switchButton = view.findViewById<MaterialSwitch>(R.id.switch_button)
        switchButton.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            sliderOne.isEnabled = isChecked
            sliderTwo.isEnabled = isChecked
            sliderThree.isEnabled = isChecked
        }
        setUpSlider(sliderOne, view.findViewById<TextView>(R.id.value_1), "%.0f")
        setUpSlider(sliderTwo, view.findViewById<TextView>(R.id.value_2), "%.0f")
        setUpSlider(sliderThree, view.findViewById<TextView>(R.id.value_3), "%.2f")
        return view
    }

    private fun setUpSlider(slider: Slider, textView: TextView, valueFormat: String) {
        slider.addOnChangeListener(Slider.OnChangeListener { slider1: Slider?, value: Float, fromUser: Boolean ->
            textView.text = String.format(valueFormat, value)
        })
        slider.value = slider.valueFrom
    }
}
