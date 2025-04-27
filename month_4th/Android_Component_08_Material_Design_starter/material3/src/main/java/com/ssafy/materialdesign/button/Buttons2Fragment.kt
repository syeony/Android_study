package com.ssafy.materialdesign.button

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.databinding.FragmentButtons2Binding
import com.ssafy.materialdesign.util.CommonUtil.findViewsWithType

private const val TAG = "Buttons2Activity_싸피"
class Buttons2Fragment : BaseFragment() {

    private var _binding: FragmentButtons2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateFragmentView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        _binding = FragmentButtons2Binding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getActionBarTitle(): String {
        return "버튼 예제 2"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttons: List<MaterialButton> = findViewsWithType(binding.root, MaterialButton::class.java)
        for (button in buttons) {
            button.setOnClickListener {
                Toast.makeText(requireContext(), "button clicked..", Toast.LENGTH_SHORT).show()
            }
        }

        val toggleGroups: List<MaterialButtonToggleGroup> = findViewsWithType( binding.root, MaterialButtonToggleGroup::class.java )
        //enabled toggle시 전체 enable or disable..
        binding.switchEnable.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            Log.d(TAG, "onCreate: ${toggleGroups.size}, ${isChecked}")
            for (toggleGroup in toggleGroups) {
                // Enable the button group if enable toggle is checked.
                toggleGroup.isEnabled = isChecked
            }
        }
    }
}