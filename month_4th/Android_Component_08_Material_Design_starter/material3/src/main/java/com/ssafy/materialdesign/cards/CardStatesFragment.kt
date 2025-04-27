package com.ssafy.materialdesign.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.google.android.material.card.MaterialCardView
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class CardStatesFragment : BaseFragment() {

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_card_states, viewGroup, false)
        val radioGroup = view.findViewById<RadioGroup>(R.id.cat_card_radio_group)
        val card = view.findViewById<MaterialCardView>(R.id.card)
        val checkableCard = view.findViewById<MaterialCardView>(R.id.checkable_card)

        checkableCard.setOnLongClickListener { v: View? ->
            checkableCard.isChecked = !checkableCard.isChecked
            true
        }

        radioGroup.setOnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            card.isHovered = checkedId == R.id.hovered
            card.isPressed = checkedId == R.id.pressed
            checkableCard.isHovered = checkedId == R.id.hovered
            checkableCard.isPressed = checkedId == R.id.pressed
        }

        return view
    }

}