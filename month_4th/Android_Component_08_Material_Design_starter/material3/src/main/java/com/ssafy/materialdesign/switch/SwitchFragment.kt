package com.ssafy.materialdesign.switch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class SwitchFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View =layoutInflater.inflate(R.layout.fragment_material_switch, viewGroup, false /* attachToRoot */)
        val switchDemoViewGroup = view.findViewById<ViewGroup>(R.id.main_viewGroup)
        val toggledView: View =layoutInflater.inflate(R.layout.material_switch_toggled, switchDemoViewGroup, false)
        switchDemoViewGroup.addView(toggledView)

        val toggledSwitches: List<MaterialSwitch> = CommonUtil.findViewsWithType(toggledView,MaterialSwitch::class.java)
        val switchToggle = view.findViewById<MaterialSwitch>(R.id.switch_toggle)
        switchToggle.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            for (switchMaterial in toggledSwitches) {
                switchMaterial.isEnabled = isChecked
            }
        }
        return view
    }
}
