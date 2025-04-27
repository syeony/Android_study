package com.ssafy.materialdesign.bottomappbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.databinding.FragmentM2styleBottomAppBarBinding

class M2StyleBottomAppBarFragment : BaseFragment() {

    private var _binding: FragmentM2styleBottomAppBarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        _binding = FragmentM2styleBottomAppBarBinding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            Snackbar.make(it, "FAB Clicked..", Snackbar.LENGTH_SHORT).show()
        }
    }
}