package com.ssafy.fragment_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.ssafy.fragment.tab_fragment.TabItemFragment3
import com.ssafy.fragment_2.databinding.ActivityTabLayoutBinding
import com.ssafy.fragment_2.tab_fragment.TabItemFragment1
import com.ssafy.fragment_2.tab_fragment.TabItemFragment2
import com.ssafy.fragment_2.tab_fragment.TabItemFragment4
import com.ssafy.fragment_2.tab_fragment.TabItemFragment5

private const val TAG = "TabLayoutActivity_싸피"
class TabLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_main, TabItemFragment1())
            .commit()

        binding.tabLayoutMain.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position){
                    0 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, TabItemFragment1())
                        .commit()

                    1 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, TabItemFragment2())
                        .commit()

                    2 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, TabItemFragment3())
                        .commit()

                    3 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, TabItemFragment4())
                        .commit()

                    4 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, TabItemFragment5())
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}

