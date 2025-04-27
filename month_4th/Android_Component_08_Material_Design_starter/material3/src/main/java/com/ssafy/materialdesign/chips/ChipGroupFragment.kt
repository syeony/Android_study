package com.ssafy.materialdesign.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.LayoutRes
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class ChipGroupFragment : BaseFragment() {

    private lateinit var singleSelectionSwitch: MaterialSwitch

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view = layoutInflater.inflate(R.layout.fragment_chip_group, viewGroup, false )
        singleSelectionSwitch = view.findViewById(R.id.single_selection)

        val reflowGroup = view.findViewById<ChipGroup>(R.id.reflow_group)
        val scrollGroup = view.findViewById<ChipGroup>(R.id.scroll_group)

        singleSelectionSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            reflowGroup.isSingleSelection = isChecked
            scrollGroup.isSingleSelection = isChecked
            initChipGroup(reflowGroup)
            initChipGroup(scrollGroup)
        }
        initChipGroup(reflowGroup)
        initChipGroup(scrollGroup)

        val fab = view.findViewById<FloatingActionButton>(R.id.cat_chip_group_refresh)
        fab.setOnClickListener { v: View? ->
            // Reload the chip group UI.
            initChipGroup(reflowGroup)
            initChipGroup(scrollGroup)
        }
        return view
    }


    @LayoutRes
    protected fun getChipGroupItem(singleSelection: Boolean): Int {
        return if (singleSelection) R.layout.chip_group_item_choice else R.layout.chip_group_item_filter
    }

    private fun initChipGroup(chipGroup: ChipGroup) {
        chipGroup.removeAllViews()
        val singleSelection = singleSelectionSwitch.isChecked
        val textArray = resources.getStringArray(R.array.cat_chip_group_text_array)
        for (text in textArray) {
            val chip = layoutInflater.inflate(getChipGroupItem(singleSelection), chipGroup, false) as Chip
            chip.text = text
            chip.isCloseIconVisible = true
            chip.setOnCloseIconClickListener { v: View? ->
                chipGroup.removeView(
                    chip
                )
            }
            chipGroup.addView(chip)
        }
    }
}
