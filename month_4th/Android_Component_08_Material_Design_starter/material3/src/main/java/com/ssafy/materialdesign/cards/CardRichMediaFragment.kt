package com.ssafy.materialdesign.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class CardRichMediaFragment : BaseFragment() {

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_card_rich_media, viewGroup, false)

        return view
    }

}