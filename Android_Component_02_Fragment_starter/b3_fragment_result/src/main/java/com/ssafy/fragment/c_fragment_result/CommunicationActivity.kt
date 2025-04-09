package com.ssafy.fragment.c_fragment_result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import com.ssafy.fragment.c_fragment_result.databinding.ActivityCommunicationBinding

class CommunicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommunicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_for_comm, CommunicationFragment()).commit()

    }

}