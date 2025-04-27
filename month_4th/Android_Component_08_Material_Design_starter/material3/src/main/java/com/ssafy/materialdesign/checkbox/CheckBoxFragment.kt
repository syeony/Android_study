package com.ssafy.materialdesign.checkbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.checkbox.MaterialCheckBox.OnCheckedStateChangedListener
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class CheckBoxFragment : BaseFragment() {


    private var isUpdatingChildren = false

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_checkbox, viewGroup, false )
        val toggleContainer = view.findViewById<ViewGroup>(R.id.checkbox_toggle_container)

        val toggledCheckBoxes: List<CheckBox> = CommonUtil.findViewsWithType(toggleContainer, CheckBox::class.java)
        val allCheckBoxes: List<MaterialCheckBox> = CommonUtil.findViewsWithType(view, MaterialCheckBox::class.java)

        // 하위 checkbox enable/disable 처리
        val checkBoxToggle = view.findViewById<CheckBox>(R.id.checkbox_toggle)
        checkBoxToggle.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            for (cb in toggledCheckBoxes) {
                cb.isEnabled = isChecked
            }
        }

        // 화면에 모든 체크박스 에러 표시
        val checkBoxToggleError = view.findViewById<CheckBox>(R.id.checkbox_toggle_error)
        checkBoxToggleError.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            for (cb in allCheckBoxes) {
                cb.isErrorShown = isChecked
            }
        }

        val firstChild = view.findViewById<CheckBox>(R.id.checkbox_child_1)
        firstChild.isChecked = true
        val indeterminateContainer = view.findViewById<ViewGroup>(R.id.checkbox_indeterminate_container)
        val childrenCheckBoxes: List<CheckBox> = CommonUtil.findViewsWithType(indeterminateContainer,CheckBox::class.java)

        // Parent checkbox가 선택/해제되면 자식들 모두 선택/해제
        val checkBoxParent = view.findViewById<MaterialCheckBox>(R.id.checkbox_parent)
        val parentOnCheckedStateChangedListener =
            OnCheckedStateChangedListener { checkBox: MaterialCheckBox, state: Int ->
                val isChecked = checkBox.isChecked
                if (state != MaterialCheckBox.STATE_INDETERMINATE) {
                    isUpdatingChildren = true
                    for (child in childrenCheckBoxes) {
                        child.isChecked = isChecked
                    }
                    isUpdatingChildren = false
                }
            }
        checkBoxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)

        val childOnCheckedStateChangedListener =
            OnCheckedStateChangedListener { checkBox: MaterialCheckBox?, state: Int ->
                if (isUpdatingChildren) {
                    return@OnCheckedStateChangedListener
                }
                setParentState(
                    checkBoxParent,
                    childrenCheckBoxes,
                    parentOnCheckedStateChangedListener
                )
            }
        for (child in childrenCheckBoxes) {
            (child as MaterialCheckBox).addOnCheckedStateChangedListener(childOnCheckedStateChangedListener)
        }
        setParentState(checkBoxParent, childrenCheckBoxes, parentOnCheckedStateChangedListener)
        return view
    }

    private fun setParentState(
        checkBoxParent: MaterialCheckBox,
        childrenCheckBoxes: List<CheckBox>,
        parentOnCheckedStateChangedListener: OnCheckedStateChangedListener
    ) {
        var allChecked = true
        var noneChecked = true
        for (child in childrenCheckBoxes) {
            if (!child.isChecked) {
                allChecked = false
            } else {
                noneChecked = false
            }
            if (!allChecked && !noneChecked) {
                break
            }
        }
        checkBoxParent.removeOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
        if (allChecked) {
            checkBoxParent.isChecked = true
        } else if (noneChecked) {
            checkBoxParent.isChecked = false
        } else {
            checkBoxParent.checkedState = MaterialCheckBox.STATE_INDETERMINATE
        }
        checkBoxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
    }
}
