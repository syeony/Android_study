package com.ssafy.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.fragment.databinding.FragmentABinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val TAG = "AFragment_싸피"
class AFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding:FragmentABinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    //view 생성
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_a, container, false)
        _binding = FragmentABinding.inflate(inflater,container,false)
        return _binding?.root
    }

    //UI 초기화
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: A")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        //newInstance쓰는 이유: Framework에 의해 제거되는 데이터를 bundle로 전달하면 재생성시 전달됨
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}