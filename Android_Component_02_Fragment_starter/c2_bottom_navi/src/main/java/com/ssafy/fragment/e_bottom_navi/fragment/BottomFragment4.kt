package com.ssafy.fragment.e_bottom_navi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.fragment.e_bottom_navi.databinding.FragmentBottom1Binding
import com.ssafy.fragment.e_bottom_navi.databinding.FragmentBottom4Binding

class BottomFragment4 : Fragment() {

    var _binding : FragmentBottom4Binding? = null
    val binding : FragmentBottom4Binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottom4Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}