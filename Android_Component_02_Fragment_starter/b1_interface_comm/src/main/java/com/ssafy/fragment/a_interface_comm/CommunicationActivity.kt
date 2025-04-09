package com.ssafy.fragment.a_interface_comm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.fragment.a_interface_comm.databinding.ActivityCommunicationBinding

class CommunicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommunicationBinding
    private lateinit var communicationFragment: CommunicationFragment
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        communicationFragment = CommunicationFragment()

        //인터페이스에 있는 함수 구현한 거
        communicationFragment.listener = object:CommunicationCallback{
            override fun onPlusCount() {
                count++
                binding.countTv.text = count.toString()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_for_comm, communicationFragment)
            .commit()

        binding.countTv.text = "Count : $count"
    }

    //이렇게 프래그먼트에 전달해도 되지만 좀 거시기함
//    fun plus1(){
//        count++
//        binding.countTv.text = count.toString()
//    }

}