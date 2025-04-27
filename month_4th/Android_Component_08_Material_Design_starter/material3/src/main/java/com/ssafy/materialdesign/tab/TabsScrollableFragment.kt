package com.ssafy.materialdesign.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.annotation.LayoutRes
import com.google.android.material.tabs.TabLayout
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class TabsScrollableFragment : BaseFragment() {


    private val KEY_TABS = "TABS"
    private val KEY_TAB_GRAVITY = "TAB_GRAVITY"

    private var numTabs = 0
    private lateinit var tabTitles: Array<String>
    private lateinit var scrollableTabLayout: TabLayout

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_tabs_scrollable, viewGroup, false)
        val content = view.findViewById<ViewGroup>(R.id.content)

        val tabsContent =layoutInflater.inflate(R.layout.tabs_scrollable_content, content, false )
        content.addView(tabsContent, 0)

        scrollableTabLayout = tabsContent.findViewById(R.id.scrollable_tab_layout)
        val tabGravityStartButton = view.findViewById<RadioButton>(R.id.tabs_gravity_start_button)
        tabGravityStartButton.setOnClickListener { v: View? ->
            scrollableTabLayout.tabGravity = TabLayout.GRAVITY_START
        }

        val tabGravityCenterButton = view.findViewById<RadioButton>(R.id.tabs_gravity_center_button)
        tabGravityCenterButton.setOnClickListener { v: View? ->
            scrollableTabLayout.tabGravity = TabLayout.GRAVITY_CENTER
        }

        if (bundle != null) {
            scrollableTabLayout.removeAllTabs()
            scrollableTabLayout.tabGravity = bundle.getInt(KEY_TAB_GRAVITY)
            // Restore saved tabs
            val tabLabels = bundle.getStringArray(KEY_TABS)
            for (label in tabLabels!!) {
                scrollableTabLayout.addTab(scrollableTabLayout.newTab().setText(label))
            }
        }

        numTabs = scrollableTabLayout.getTabCount()
        tabTitles = requireContext().resources.getStringArray(R.array.cat_tabs_titles)

        val addButton = view.findViewById<Button>(R.id.add_tab_button)
        addButton.setOnClickListener { v: View? ->
            scrollableTabLayout.addTab(
                scrollableTabLayout.newTab().setText(tabTitles[numTabs % tabTitles.size])
            )
            numTabs++
        }

        val removeFirstButton = view.findViewById<Button>(R.id.remove_first_tab_button)
        removeFirstButton.setOnClickListener { v: View? ->
            if (scrollableTabLayout.tabCount > 0) {
                scrollableTabLayout.removeTabAt(0)
            }
            numTabs = Math.max(0, numTabs - 1)
        }

        val removeLastButton = view.findViewById<Button>(R.id.remove_last_tab_button)
        removeLastButton.setOnClickListener { v: View? ->
            val tab =
                scrollableTabLayout.getTabAt(scrollableTabLayout.getTabCount() - 1)
            if (tab != null) {
                scrollableTabLayout.removeTab(tab)
            }
            numTabs = Math.max(0, numTabs - 1)
        }
        return view
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        val tabLabels = arrayOfNulls<String>(scrollableTabLayout.tabCount)
        for (i in 0 until scrollableTabLayout.tabCount) {
            tabLabels[i] = scrollableTabLayout.getTabAt(i)!!.text.toString()
        }
        bundle.putStringArray(KEY_TABS, tabLabels)
        bundle.putInt(KEY_TAB_GRAVITY, scrollableTabLayout.tabGravity)
    }
}
