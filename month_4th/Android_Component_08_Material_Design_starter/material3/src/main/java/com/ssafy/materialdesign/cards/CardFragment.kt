package com.ssafy.materialdesign.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.databinding.FragmentCardBinding

class CardFragment : BaseFragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(layoutInflater, viewGroup, false);
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}