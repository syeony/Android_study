package com.ssafy.fragment.a_interface_comm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.fragment.a_interface_comm.databinding.FragmentCommunicationBinding

class CommunicationFragment : Fragment() {

    //인터페이스와 연결하기위해 listener선언
    lateinit var listener: CommunicationCallback

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

        binding.plusButton.setOnClickListener {
            //인터페이스에 있는 함수만 호출하면 끝
            listener.onPlusCount()

            //activity.plus1호출
            //제일 간단한 방법이나 좀 거시기하다...
//            (requireActivity() as CommunicationActivity).plus1()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}