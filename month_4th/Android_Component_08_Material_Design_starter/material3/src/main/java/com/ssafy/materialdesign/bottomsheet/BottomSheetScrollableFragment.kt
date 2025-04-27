package com.ssafy.materialdesign.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.databinding.BottomsheetScrollableContentBinding
import com.ssafy.materialdesign.databinding.FragmentBottomsheetScrollableBinding

class BottomSheetScrollableFragment : BaseFragment() {
    private var _binding: FragmentBottomsheetScrollableBinding? = null
    private val binding get() = _binding!!
    private var _bindingContent: BottomsheetScrollableContentBinding? = null
    private val bindingContent get() = _bindingContent!!

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        _binding = FragmentBottomsheetScrollableBinding.inflate(layoutInflater, viewGroup, false)
        _bindingContent = BottomsheetScrollableContentBinding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up BottomSheetDialog
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bindingContent.root)
        bottomSheetDialog.behavior.peekHeight = 500 //처음 펼쳐질때 높이

        binding.bottomsheetButton.setOnClickListener { v: View? -> bottomSheetDialog.show() }

        //another way to create..
        binding.bottomsheetButton2.setOnClickListener { v: View? ->
            BottomSheetScrollableContentFragment().show(requireActivity().supportFragmentManager, "")
        }
    }
}