package com.ssafy.fragment.e_bottom_navi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.fragment.e_bottom_navi.databinding.FragmentBottom1Binding


class BottomFragment1 : Fragment() {

    private var _binding : FragmentBottom1Binding? = null
    private val binding :FragmentBottom1Binding
        get() = _binding!!
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle? ): View {
        _binding = FragmentBottom1Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}