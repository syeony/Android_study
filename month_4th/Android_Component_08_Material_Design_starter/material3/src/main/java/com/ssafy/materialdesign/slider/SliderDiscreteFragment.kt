package com.ssafy.materialdesign.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.BasicLabelFormatter
import com.google.android.material.slider.Slider
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import java.util.Arrays

class SliderDiscreteFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View =layoutInflater.inflate(R.layout.fragment_slider_discrete, viewGroup, false /* attachToRoot */)
        val withLabelFormatter = view.findViewById<Slider>(R.id.slider_4)
        withLabelFormatter.setLabelFormatter(BasicLabelFormatter())
        val sliders = Arrays.asList<Slider>(
            view.findViewById<Slider>(R.id.slider_1),
            view.findViewById<Slider>(R.id.slider_2),
            view.findViewById<Slider>(R.id.slider_3),
            withLabelFormatter,
            view.findViewById<Slider>(R.id.slider_5),
            view.findViewById<Slider>(R.id.slider_6)
        )
        val switchButton = view.findViewById<MaterialSwitch>(R.id.switch_button)
        switchButton.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            for (slider in sliders) {
                slider.isEnabled = isChecked
            }
        }
        return view
    }
}
