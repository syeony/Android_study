package com.ssafy.materialdesign.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.chip.Chip
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class ChipsFragment : BaseFragment() {

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_chip, viewGroup, false)
        val content = view.findViewById<ViewGroup>(R.id.content)

        View.inflate(context, R.layout.chip_content, content)
        val chips: List<Chip> = CommonUtil.findViewsWithType(view, Chip::class.java)
        for (chip in chips) {
            chip.setOnCloseIconClickListener { v: View? ->
                Snackbar.make(view,"Clicked close icon.",Snackbar.LENGTH_SHORT).show()
            }
            if (chip.isEnabled && !chip.isCheckable) {
                chip.setOnClickListener { v: View? ->
                    Snackbar.make(view, "Activated chip.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        val longTextSwitch = view.findViewById<MaterialSwitch>(R.id.cat_chip_text_length_switch)
        longTextSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            val updatedText =
                getText(if (isChecked) R.string.cat_chip_text_to_truncate else R.string.cat_chip_text)
            for (chip in chips) {
                chip.text = updatedText
            }
        }
        val enabledSwitch = view.findViewById<MaterialSwitch>(R.id.cat_chip_enabled_switch)
        enabledSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            for (chip in chips) {
                chip.isEnabled = isChecked
            }
        }
        return view
    }

}
