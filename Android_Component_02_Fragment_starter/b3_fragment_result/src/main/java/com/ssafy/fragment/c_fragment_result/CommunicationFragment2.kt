package com.ssafy.fragment.c_fragment_result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.ssafy.fragment.c_fragment_result.databinding.FragmentCommunication2Binding
import com.ssafy.fragment.c_fragment_result.databinding.FragmentCommunicationBinding


private const val ARG_PARAM1 = "param1"
private const val TAG = "CommunicationFragment2_싸피"

class CommunicationFragment2 : Fragment() {
    private var param1: String? = null

    private var _binding: FragmentCommunication2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCommunication2Binding.inflate(inflater, container, false)
        binding.addressInput.setText(param1)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.preBtn.setOnClickListener {
            // 입력받은 주소정보를 FragmentResult에 셋
            setFragmentResult("COMM_WITH_FRAG",
                bundleOf("Address" to binding.addressInput.text.toString())
            )

           requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout_for_comm, CommunicationFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(byParam:String) =
            CommunicationFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, byParam)
                }
            }
    }
}