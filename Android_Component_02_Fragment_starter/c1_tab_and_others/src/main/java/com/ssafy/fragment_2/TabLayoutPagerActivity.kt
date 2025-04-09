package com.ssafy.fragment_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.fragment.tab_fragment.TabItemFragment3
import com.ssafy.fragment_2.databinding.ActivityTabPager2LayoutBinding
import com.ssafy.fragment_2.tab_fragment.TabItemFragment1
import com.ssafy.fragment_2.tab_fragment.TabItemFragment2
import com.ssafy.fragment_2.tab_fragment.TabItemFragment4
import com.ssafy.fragment_2.tab_fragment.TabItemFragment5

private const val TAG = "TabLayoutActivity_싸피"
class TabLayoutPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabPager2LayoutBinding
    private val tabTitle = arrayOf("월","화","수", "목","금")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTabPager2LayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager2.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout,  binding.viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    class ViewPagerAdapter(activity: TabLayoutPagerActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return 5 //count..
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> TabItemFragment1()
                1 -> TabItemFragment2()
                2 -> TabItemFragment3()
                3 -> TabItemFragment4()
                4 -> TabItemFragment5()
                else -> TabItemFragment1()
            }
        }

    }
}

