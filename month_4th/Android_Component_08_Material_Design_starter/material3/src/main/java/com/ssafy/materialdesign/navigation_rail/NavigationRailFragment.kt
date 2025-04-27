package com.ssafy.materialdesign.navigation_rail

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
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
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigationrail.NavigationRailView
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class NavigationRailFragment : BaseFragment() {

    private val MAX_NAVIGATION_RAIL_CHILDREN = 7

    private val MENU_GRAVITY_TOP = Gravity.TOP + Gravity.CENTER_HORIZONTAL
    private val MENU_GRAVITY_CENTER = Gravity.CENTER
    private val MENU_GRAVITY_BOTTOM = Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL

    private val badgeGravityValues: IntArray = intArrayOf(
        BadgeDrawable.TOP_END,
        BadgeDrawable.TOP_START,
        BadgeDrawable.BOTTOM_END,
        BadgeDrawable.BOTTOM_START
    )

    private var numVisibleChildren = 3
    private lateinit var navigationRailView: NavigationRailView

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_navigation_rail, viewGroup, false )
        navigationRailView = view.findViewById(R.id.cat_navigation_rail)

        initNavigationRail(requireContext(), view)
        initNavigationRailDemoControls(view)
        val navigationItemListener = NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            handleAllNavigationRailSelections(item.itemId)
            val page1Text = view.findViewById<TextView>(R.id.page_1)
            val page2Text = view.findViewById<TextView>(R.id.page_2)
            val page3Text = view.findViewById<TextView>(R.id.page_3)
            val page4Text = view.findViewById<TextView>(R.id.page_4)
            val page5Text = view.findViewById<TextView>(R.id.page_5)
            val page6Text = view.findViewById<TextView>(R.id.page_6)
            val page7Text = view.findViewById<TextView>(R.id.page_7)
            val itemId = item.itemId
            page1Text.visibility = if (itemId == R.id.action_page_1) View.VISIBLE else View.GONE
            page2Text.visibility = if (itemId == R.id.action_page_2) View.VISIBLE else View.GONE
            page3Text.visibility = if (itemId == R.id.action_page_3) View.VISIBLE else View.GONE
            page4Text.visibility = if (itemId == R.id.action_page_4) View.VISIBLE else View.GONE
            page5Text.visibility = if (itemId == R.id.action_page_5) View.VISIBLE else View.GONE
            page6Text.visibility = if (itemId == R.id.action_page_6) View.VISIBLE else View.GONE
            page7Text.visibility = if (itemId == R.id.action_page_7) View.VISIBLE else View.GONE
            clearAndHideBadge(item.itemId)
            false
        }
        setNavigationRailListeners(navigationItemListener)
        if (bundle == null) {
            setupBadging()
        }
        return view
    }

    private fun setupBadging() {
        var menuItemId = navigationRailView.menu.getItem(0).itemId
        // An icon only badge will be displayed.
        var badge = navigationRailView.getOrCreateBadge(menuItemId)
        badge.isVisible = true
        menuItemId = navigationRailView.menu.getItem(1).itemId
        // A badge with the text "99" will be displayed.
        badge = navigationRailView.getOrCreateBadge(menuItemId)
        badge.isVisible = true
        badge.number = 99
        menuItemId = navigationRailView.menu.getItem(2).itemId
        // A badge with the text "999+" will be displayed.
        badge = navigationRailView.getOrCreateBadge(menuItemId)
        badge.isVisible = true
        badge.number = 9999
    }

    private fun updateBadgeNumber(delta: Int) {
        // Increase the badge number on the first menu item.
        val menuItem = navigationRailView.menu.getItem(0)
        val menuItemId = menuItem.itemId
        val badgeDrawable = navigationRailView.getOrCreateBadge(menuItemId)
        // In case the first menu item has been selected and the badge was hidden, call
        // BadgeDrawable#setVisible() to ensure the badge is visible.
        badgeDrawable.isVisible = true
        badgeDrawable.number = badgeDrawable.number + delta
    }

    private fun updateBadgeGravity(badgeGravity: Int) {
        for (i in 0 until navigationRailView.menu.size()) {
            // Update the badge gravity on all the menu items.
            val menuItem = navigationRailView.menu.getItem(i)
            val menuItemId = menuItem.itemId
            val badgeDrawable = navigationRailView.getBadge(menuItemId)
            if (badgeDrawable != null) {
                badgeDrawable.badgeGravity = badgeGravity
            }
        }
    }

    private fun clearAndHideBadge(menuItemId: Int) {
        val menuItem = navigationRailView.menu.getItem(0)
        if (menuItem.itemId == menuItemId) {
            // Hide instead of removing the badge associated with the first menu item because the user
            // can trigger it to be displayed again.
            val badgeDrawable = navigationRailView.getBadge(menuItemId)
            if (badgeDrawable != null) {
                badgeDrawable.isVisible = false
                badgeDrawable.clearNumber()
            }
        } else {
            // Remove the badge associated with this menu item because cannot be displayed again.
            navigationRailView.removeBadge(menuItemId)
        }
    }

    private fun handleAllNavigationRailSelections(itemId: Int) {
        handleNavigationRailItemSelections(navigationRailView, itemId)
    }

    private fun handleNavigationRailItemSelections(view: NavigationRailView, itemId: Int) {
        view.menu.findItem(itemId).isChecked = true
    }

    protected fun initNavigationRailDemoControls(view: View) {
        initAddNavItemButton(view.findViewById(R.id.add_button))
        initRemoveNavItemButton(view.findViewById(R.id.remove_button))
        initAddIncreaseBadgeNumberButton(view.findViewById(R.id.increment_badge_number_button))
        initAddRemoveHeaderViewButtons(view)
        initMenuGravityButtons(view)
        initLabelVisibilityModeButtons(view)
        initIconSlider(view)

        val badgeGravitySpinner = view.findViewById<Spinner>(R.id.badge_gravity_spinner)
        val adapter = ArrayAdapter.createFromResource(
            badgeGravitySpinner.context,
            R.array.cat_navigation_rail_badge_gravity_titles,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        badgeGravitySpinner.adapter = adapter
        badgeGravitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                updateBadgeGravity(
                    badgeGravityValues[MathUtils.clamp(position, 0, badgeGravityValues.size - 1)]
                )
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

    private fun initAddNavItemButton(addNavItemButton: Button) {
        addNavItemButton.setOnClickListener { v: View? ->
            if (numVisibleChildren < MAX_NAVIGATION_RAIL_CHILDREN) {
                addNavItemsToNavigationRails()
                numVisibleChildren++
            }
        }
    }

    private fun initRemoveNavItemButton(removeNavItemButton: Button) {
        removeNavItemButton.setOnClickListener { v: View? ->
            if (numVisibleChildren > 0) {
                numVisibleChildren--
                removeNavItemsFromNavigationRails()
            }
        }
    }

    private fun setNavigationRailListeners(listener: NavigationBarView.OnItemSelectedListener) {
        navigationRailView.setOnItemSelectedListener(listener)
    }

    private fun removeNavItemsFromNavigationRails() {
        navigationRailView.menu.getItem(numVisibleChildren).isVisible = false
    }

    private fun addNavItemsToNavigationRails() {
        navigationRailView.menu.getItem(numVisibleChildren).isVisible = true
    }

    private fun initNavigationRail(context: Context, view: View) {
        val controlsView = view.findViewById<ViewGroup>(R.id.demo_controls)
        View.inflate(context, R.layout.navigation_rail_demo_controls, controlsView)
    }


    private fun initAddRemoveHeaderViewButtons(view: View) {
        val addHeaderViewButton = view.findViewById<Button>(R.id.add_header_view_button)
        val removeHeaderViewButton = view.findViewById<Button>(R.id.remove_header_view_button)
        addHeaderViewButton.setOnClickListener { v: View? ->
            navigationRailView.addHeaderView(R.layout.navigation_rail_header_view)
            navigationRailView.headerView!!.setOnClickListener {
                Snackbar.make(navigationRailView, "addHeaderViewButton clicked.", Snackbar.LENGTH_SHORT).show()
            }
            addHeaderViewButton.visibility = View.GONE
            removeHeaderViewButton.visibility = View.VISIBLE
        }
        removeHeaderViewButton.setOnClickListener { v: View? ->
            navigationRailView.removeHeaderView()
            addHeaderViewButton.visibility = View.VISIBLE
            removeHeaderViewButton.visibility = View.GONE
        }
    }

    private fun initMenuGravityButtons(view: View) {
        setMenuGravityClickListener(
            view,
            R.id.menu_gravity_top_button,
            MENU_GRAVITY_TOP
        )
        setMenuGravityClickListener(
            view,
            R.id.menu_gravity_center_button,
            MENU_GRAVITY_CENTER
        )
        setMenuGravityClickListener(
            view,
            R.id.menu_gravity_bottom_button,
            MENU_GRAVITY_BOTTOM
        )
    }

    private fun setMenuGravityClickListener(view: View, buttonId: Int, gravity: Int) {
        view.findViewById<View>(buttonId).setOnClickListener { v: View? ->
            navigationRailView.menuGravity = gravity
        }
    }

    private fun initLabelVisibilityModeButtons(view: View) {
        setLabelVisibilityClickListener(
            view,
            R.id.label_mode_auto_button,
            NavigationBarView.LABEL_VISIBILITY_AUTO
        )
        setLabelVisibilityClickListener(
            view, R.id.label_mode_selected_button, NavigationBarView.LABEL_VISIBILITY_SELECTED
        )
        setLabelVisibilityClickListener(
            view,
            R.id.label_mode_labeled_button,
            NavigationBarView.LABEL_VISIBILITY_LABELED
        )
        setLabelVisibilityClickListener(
            view, R.id.label_mode_unlabeled_button, NavigationBarView.LABEL_VISIBILITY_UNLABELED
        )
    }

    private fun setLabelVisibilityClickListener(view: View, buttonId: Int, mode: Int) {
        view.findViewById<View>(buttonId)
            .setOnClickListener { v: View? ->
                navigationRailView.labelVisibilityMode = mode
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
                    navigationRailView.itemIconSize = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, progress.toFloat(), displayMetrics
                    ).toInt()
                    iconSizeTextView.text = progress.toString() + iconSizeUnit
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
    }
}