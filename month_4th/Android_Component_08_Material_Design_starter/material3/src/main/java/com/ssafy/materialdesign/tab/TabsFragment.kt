package com.ssafy.materialdesign.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.LayoutRes
import androidx.core.math.MathUtils
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeDrawable.BadgeGravity
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class TabsFragment : BaseFragment() {


    private lateinit var tabLayouts: List<TabLayout>

    private val badgeGravityValues: IntArray = intArrayOf(
        BadgeDrawable.TOP_END,
        BadgeDrawable.TOP_START,
        BadgeDrawable.BOTTOM_END,
        BadgeDrawable.BOTTOM_START
    )

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view = layoutInflater.inflate(R.layout.fragment_tabs_content, viewGroup,  false)

        tabLayouts = CommonUtil.findViewsWithType(view, TabLayout::class.java)

        val incrementBadgeNumberButton =view.findViewById<MaterialButton>(R.id.increment_badge_number_button)
        incrementBadgeNumberButton.setOnClickListener { v: View? -> incrementBadgeNumber() }
        val badgeGravitySpinner = view.findViewById<Spinner>(R.id.badge_gravity_spinner)
        val adapter = ArrayAdapter.createFromResource(
            badgeGravitySpinner.context,
            R.array.cat_tabs_badge_gravity_titles,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        badgeGravitySpinner.adapter = adapter
        badgeGravitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                updateBadgeGravity(badgeGravityValues[MathUtils.clamp(position, 0, badgeGravityValues.size - 1)])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        setupBadging()
        return view
    }

    private fun setupBadging() {
        for (tabLayout in tabLayouts) {
            // An icon only badge will be displayed.
            var badgeDrawable = tabLayout.getTabAt(0)!!.orCreateBadge
            badgeDrawable.isVisible = true

            // A badge with the text "99" will be displayed.
            badgeDrawable = tabLayout.getTabAt(1)!!.orCreateBadge
            badgeDrawable.isVisible = true
            badgeDrawable.number = 99

            // A badge with the text "999+" will be displayed.
            badgeDrawable = tabLayout.getTabAt(2)!!.orCreateBadge
            badgeDrawable.isVisible = true
            badgeDrawable.number = 9999
            tabLayout.addOnTabSelectedListener(
                object : OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        clearAndHideBadge(tab.position)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {}
                    override fun onTabReselected(tab: TabLayout.Tab) {
                        clearAndHideBadge(tab.position)
                    }
                })
        }
    }

    private fun incrementBadgeNumber() {
        for (tabLayout in tabLayouts) {
            // Increase the badge number on the first tab position.
            // In case the first tab has been selected and the badge was hidden, call
            // BadgeDrawable#setVisible() to ensure the badge is visible.
            val badgeDrawable = tabLayout.getTabAt(0)!!.orCreateBadge
            badgeDrawable.isVisible = true
            badgeDrawable.number = badgeDrawable.number + 1
        }
    }

    private fun clearAndHideBadge(tabPosition: Int) {
        for (tabLayout in tabLayouts) {
            if (tabPosition == 0) {
                // Hide instead of removing the badge associated with the first menu item because the user
                // can trigger it to be displayed again.
                val badgeDrawable = tabLayout.getTabAt(tabPosition)!!.badge
                if (badgeDrawable != null) {
                    badgeDrawable.isVisible = false
                    badgeDrawable.clearNumber()
                }
            } else {
                tabLayout.getTabAt(tabPosition)!!.removeBadge()
            }
        }
    }

    private fun updateBadgeGravity(@BadgeGravity badgeGravity: Int) {
        for (tabLayout in tabLayouts) {
            // Update the badge gravity on all the tabs.
            for (index in 0 until tabLayout.tabCount) {
                val badgeDrawable = tabLayout.getTabAt(index)!!.badge
                if (badgeDrawable != null) {
                    badgeDrawable.badgeGravity = badgeGravity
                }
            }
        }
    }
}
