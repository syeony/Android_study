package com.ssafy.cleanstore.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ssafy.cleanstore.MainActivity
import com.ssafy.cleanstore.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    //타입캐스팅
    lateinit var myActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as MainActivity
    }

    private var _binding : FragmentMainBinding?=null
    private val binding : FragmentMainBinding
        get() = _binding!!

    private var isBottomNavVisible = true // 초기값: 보이는 상태

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.one.setOnClickListener {
            isBottomNavVisible = !isBottomNavVisible
            myActivity.toggleBottomNavigationView(isBottomNavVisible)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}