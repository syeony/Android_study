package com.ssafy.materialdesign.navigation_bar

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.TextView
import androidx.core.math.MathUtils
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeDrawable.BadgeGravity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationBarView
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class NavigationBarFragment : BaseFragment() {

    private val MAX_BOTTOM_NAV_CHILDREN = 5

    private val badgeGravityValues: IntArray = intArrayOf(
        BadgeDrawable.TOP_END,
        BadgeDrawable.TOP_START,
        BadgeDrawable.BOTTOM_END,
        BadgeDrawable.BOTTOM_START
    )

    private var numVisibleChildren = 3
    private lateinit var bottomNavigationViews: List<BottomNavigationView>


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = layoutInflater.inflate( R.layout.fragment_navigation_bar, container, false )
        initBottomNavs(layoutInflater, view)
        initBottomNavDemoControls(view)

        val navigationItemListener = NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            handleAllBottomNavSelections(item.itemId)
            val page1Text = view.findViewById<TextView>(R.id.page_1)
            val page2Text = view.findViewById<TextView>(R.id.page_2)
            val page3Text = view.findViewById<TextView>(R.id.page_3)
            val page4Text = view.findViewById<TextView>(R.id.page_4)
            val page5Text = view.findViewById<TextView>(R.id.page_5)
            val itemId = item.itemId
            page1Text.visibility = if (itemId == R.id.action_page_1) View.VISIBLE else View.GONE
            page2Text.visibility = if (itemId == R.id.action_page_2) View.VISIBLE else View.GONE
            page3Text.visibility = if (itemId == R.id.action_page_3) View.VISIBLE else View.GONE
            page4Text.visibility = if (itemId == R.id.action_page_4) View.VISIBLE else View.GONE
            page5Text.visibility = if (itemId == R.id.action_page_5) View.VISIBLE else View.GONE
            clearAndHideBadge(item.itemId)
            false
        }
        setBottomNavListeners(navigationItemListener)
        if (savedInstanceState == null) {
            setupBadging()
        }
        return view
    }

    // 0번, 1번, 2번 탭 기본 뱃지 숫자 세팅
    private fun setupBadging() {
        for (bn in bottomNavigationViews) {
            var menuItemId = bn.menu.getItem(0).itemId
            // An icon only badge will be displayed.
            var badge = bn.getOrCreateBadge(menuItemId)
            badge.isVisible = true
            menuItemId = bn.menu.getItem(1).itemId
            // A badge with the text "99" will be displayed.
            badge = bn.getOrCreateBadge(menuItemId)
            badge.isVisible = true
            badge.number = 99
            menuItemId = bn.menu.getItem(2).itemId
            // A badge with the text "999+" will be displayed.
            badge = bn.getOrCreateBadge(menuItemId)
            badge.isVisible = true
            badge.number = 9999
        }
    }

    // 0번 index 뱃지 숫자 증가.
    private fun updateBadgeNumber(delta: Int) {
        for (bn in bottomNavigationViews) {
            // Increase the badge number on the first menu item.
            val menuItem = bn.menu.getItem(0)
            val menuItemId = menuItem.itemId
            val badgeDrawable = bn.getOrCreateBadge(menuItemId)
            // In case the first menu item has been selected and the badge was hidden, call
            // BadgeDrawable#setVisible() to ensure the badge is visible.
            badgeDrawable.isVisible = true
            badgeDrawable.number = badgeDrawable.number + delta
        }
    }

    private fun updateBadgeGravity(@BadgeGravity badgeGravity: Int) {
        for (bn in bottomNavigationViews) {
            for (i in 0 until MAX_BOTTOM_NAV_CHILDREN) {
                // Update the badge gravity on all the menu items.
                val menuItem = bn.menu.getItem(i)
                val menuItemId = menuItem.itemId
                val badgeDrawable = bn.getBadge(menuItemId)
                if (badgeDrawable != null) {
                    badgeDrawable.badgeGravity = badgeGravity
                }
            }
        }
    }

    private fun clearAndHideBadge(menuItemId: Int) {
        for (bn in bottomNavigationViews) {
            val menuItem = bn.menu.getItem(0)
            if (menuItem.itemId == menuItemId) {
                // Hide instead of removing the badge associated with the first menu item because the user
                // can trigger it to be displayed again.
                val badgeDrawable = bn.getBadge(menuItemId)
                if (badgeDrawable != null) {
                    badgeDrawable.isVisible = false
                    badgeDrawable.clearNumber()
                }
            } else {
                // Remove the badge associated with this menu item because cannot be displayed again.
                bn.removeBadge(menuItemId)
            }
        }
    }

    private fun handleAllBottomNavSelections(itemId: Int) {
        for (bn in bottomNavigationViews) {
            handleBottomNavItemSelections(bn, itemId)
        }
    }

    private fun handleBottomNavItemSelections(bn: BottomNavigationView, itemId: Int) {
        bn.menu.findItem(itemId).isChecked = true
    }

    protected fun initBottomNavDemoControls(view: View) {
        // 버튼클릭하면 tab 추가 and 삭제
        initAddNavItemButton(view.findViewById<Button>(R.id.add_button))
        initRemoveNavItemButton(view.findViewById<Button>(R.id.remove_button))
        // 뱃지 숫자 증가.
        initAddIncreaseBadgeNumberButton(view.findViewById<Button>(R.id.increment_badge_number_button))
        // 뱃지 위치 변경
        val badgeGravitySpinner = view.findViewById<Spinner>(R.id.badge_gravity_spinner)
        // Label visibility
        initLabelVisibilityModeButtons(view)
        // slider
        initIconSlider(view)

        val adapter = ArrayAdapter.createFromResource(
            badgeGravitySpinner.context,
            R.array.cat_bottom_nav_badge_gravity_titles,
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
    }

    private fun initAddIncreaseBadgeNumberButton(incrementBadgeNumberButton: Button) {
        incrementBadgeNumberButton.setOnClickListener { v: View? ->
            updateBadgeNumber(
                1
            )
        }
    }

    // Add nav item을 누르면 max갯수까지 추가됨.
    private fun initAddNavItemButton(addNavItemButton: Button) {
        addNavItemButton.setOnClickListener { v: View? ->
            if (numVisibleChildren < MAX_BOTTOM_NAV_CHILDREN) {
                addNavItemsToBottomNavs()
                numVisibleChildren++
            }
        }
    }

    private fun initRemoveNavItemButton(removeNavItemButton: Button) {
        removeNavItemButton.setOnClickListener { v: View? ->
            if (numVisibleChildren > 0) {
                numVisibleChildren--
                removeNavItemsFromBottomNavs()
            }
        }
    }

    private fun setBottomNavListeners(listener: NavigationBarView.OnItemSelectedListener) {
        for (bn in bottomNavigationViews) {
            bn.setOnItemSelectedListener(listener)
        }
    }

    private fun removeNavItemsFromBottomNavs() {
        adjustAllBottomNavItemsVisibilities(false)
    }

    private fun addNavItemsToBottomNavs() {
        adjustAllBottomNavItemsVisibilities(true)
    }

    private fun adjustAllBottomNavItemsVisibilities(visibility: Boolean) {
        for (bn in bottomNavigationViews) {
            adjustBottomNavItemsVisibility(bn, visibility)
        }
    }

    private fun adjustBottomNavItemsVisibility(bn: BottomNavigationView, visibility: Boolean) {
        bn.menu.getItem(numVisibleChildren).isVisible = visibility
    }

    private fun initBottomNavs(layoutInflater: LayoutInflater, view: View) {
        inflateBottomNavs(layoutInflater, view.findViewById(R.id.bottom_navs))
        inflateBottomNavDemoControls(layoutInflater, view.findViewById<ViewGroup>(R.id.demo_controls))
        addBottomNavsToList(view)
    }

    private fun inflateBottomNavs(layoutInflater: LayoutInflater, content: ViewGroup) {
        content.addView(layoutInflater.inflate(R.layout.cat_bottom_navs, content, false))
    }

    private fun inflateBottomNavDemoControls(layoutInflater: LayoutInflater, content: ViewGroup) {
        content.addView(layoutInflater.inflate( R.layout.navigation_bar_visibility_controls, content,false))
    }

    private fun addBottomNavsToList(view: View) {
        bottomNavigationViews = CommonUtil.findViewsWithType(view, BottomNavigationView::class.java)
    }

    private fun setAllBottomNavsLabelVisibilityMode( labelVisibilityMode: Int) {
        for (bn in bottomNavigationViews) {
            setBottomNavsLabelVisibilityMode(bn, labelVisibilityMode)
        }
    }

    private fun setBottomNavsLabelVisibilityMode(bn: BottomNavigationView,  labelVisibilityMode: Int) {
        bn.labelVisibilityMode = labelVisibilityMode
    }

    private fun setAllBottomNavsIconSize(size: Int) {
        for (bn in bottomNavigationViews) {
            bn.itemIconSize = size
        }
    }

    private fun initLabelVisibilityModeButtons(view: View) {
        initLabelVisibilityModeButton(
            view.findViewById(R.id.label_mode_auto_button),
            LabelVisibilityMode.LABEL_VISIBILITY_AUTO
        )
        initLabelVisibilityModeButton(
            view.findViewById(R.id.label_mode_selected_button),
            LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
        )
        initLabelVisibilityModeButton(
            view.findViewById(R.id.label_mode_labeled_button),
            LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        )
        initLabelVisibilityModeButton(
            view.findViewById(R.id.label_mode_unlabeled_button),
            LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
        )
    }

    private fun initLabelVisibilityModeButton(labelVisibilityModeButton: Button, labelVisibilityMode: Int) {
        labelVisibilityModeButton.setOnClickListener { v: View? ->
            setAllBottomNavsLabelVisibilityMode(
                labelVisibilityMode
            )
        }
    }

    private fun initIconSlider(view: View) {
        val iconSizeSlider = view.findViewById<SeekBar>(R.id.icon_size_slider)
        val displayMetrics = resources.displayMetrics
        val iconSizeTextView = view.findViewById<TextView>(R.id.icon_size_text_view)
        val iconSizeUnit = "dp"
        iconSizeSlider.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    setAllBottomNavsIconSize(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, progress.toFloat(), displayMetrics
                        ).toInt()
                    )
                    iconSizeTextView.text = progress.toString() + iconSizeUnit
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
    }
}
