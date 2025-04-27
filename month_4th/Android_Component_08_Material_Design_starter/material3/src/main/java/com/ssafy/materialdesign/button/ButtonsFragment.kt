package com.ssafy.materialdesign.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.databinding.FragmentButtonsBinding
import com.ssafy.materialdesign.util.CommonUtil.findViewsWithType

class ButtonsFragment : BaseFragment() {
    private var _binding: FragmentButtonsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        _binding = FragmentButtonsBinding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getActionBarTitle(): String {
        return "버튼예제"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttons: List<MaterialButton> = findViewsWithType(binding.root, MaterialButton::class.java)
        for (button in buttons) {
            button.setOnClickListener {
                Snackbar.make(it, "Button clicked...", Snackbar.LENGTH_SHORT).show();
//                Toast.makeText(requireContext(), "button clicked..", Toast.LENGTH_SHORT).show()
            }
        }
    }
}