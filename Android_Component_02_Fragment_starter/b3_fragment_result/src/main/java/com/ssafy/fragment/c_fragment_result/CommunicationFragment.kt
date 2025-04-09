package com.ssafy.fragment.c_fragment_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.ssafy.fragment.c_fragment_result.databinding.FragmentCommunicationBinding

class CommunicationFragment : Fragment() {

    private var _binding: FragmentCommunicationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fragment 가 활성화 될 때 전달받기위해 Listener를 등록한다.
        setFragmentResultListener("COMM_WITH_FRAG"){_, bundle ->
            val result = bundle.getString("Address")
            binding.addressText.text = result
        }

        binding.callFrag2Btn.setOnClickListener {
            val data = binding.addressText.text.toString()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout_for_comm, CommunicationFragment2.newInstance(data))
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}