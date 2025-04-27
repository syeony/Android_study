package com.ssafy.materialdesign.progressindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.StyleRes
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class ProgressIndicatorStandaloneFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_progress_indicator_standalone, container, false)
        //circular spec 에 resource 등 전체를 default로 둠.(별도로 지정하지 않음.)
        val spec = CircularProgressIndicatorSpec(requireContext(),  /*attrs=*/null, 0, 0)
        val chip = view.findViewById<Chip>(R.id.cat_progress_indicator_chip)
        chip.chipIcon = IndeterminateDrawable.createCircularDrawable(requireContext(), spec)
        val progressIndicatorDrawable = IndeterminateDrawable.createCircularDrawable(
            requireContext(), spec
        )

        val button = view.findViewById<MaterialButton>(R.id.cat_progress_indicator_button)
        button.setIcon(progressIndicatorDrawable)

        val chipIconSwitch = view.findViewById<MaterialSwitch>(R.id.cat_progress_indicator_standalone_chip_switch)
        chipIconSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            chip.isChipIconVisible = isChecked
            button.setIcon(if (isChecked) progressIndicatorDrawable else null)
        }
        return view
    }


}
