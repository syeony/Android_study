package com.ssafy.materialdesign.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.Spinner
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.SwitchCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class TabsControllableFragment : BaseFragment() {


    private val TAB_COUNT = 3

    @DrawableRes
    private val ICON_DRAWABLE_RES: Int = R.drawable.ic_tabs_24px

    @StringRes
    private val LABEL_STRING_RES = R.string.cat_tab_item_label

    private var showIcons = true
    private var showLabels = true
    private var tabLayouts: List<TabLayout>? = null
    private var pager: ViewPager? = null

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_tabs_controllable, viewGroup, false)

        val content = view.findViewById<ViewGroup>(R.id.content)
        val tabsContent =layoutInflater.inflate(R.layout.tabs_controllable_content, content, false )
        content.addView(tabsContent, 0)

        tabLayouts = CommonUtil.findViewsWithType(view, TabLayout::class.java)
        pager = view.findViewById(R.id.viewpager)

        val coordinatorLayout = view.findViewById<CoordinatorLayout>(R.id.coordinator_layout)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v: View?, insetsCompat: WindowInsetsCompat? ->
            val scrollable =coordinatorLayout.findViewById<View>(R.id.cat_tabs_controllable_scrollview)
            scrollable.setPadding(
                scrollable.paddingLeft,
                0,
                scrollable.paddingRight,
                scrollable.paddingBottom
            )
            insetsCompat!!
        }
        setupViewPager()
        setAllTabLayoutIcons(ICON_DRAWABLE_RES)
        setAllTabLayoutText(LABEL_STRING_RES)
        setAllTabLayoutBadges()

        val iconsToggle = view.findViewById<SwitchCompat>(R.id.toggle_icons_switch)
        iconsToggle.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            showIcons = isChecked
            setAllTabLayoutIcons(ICON_DRAWABLE_RES)
        }

        val labelsToggle = view.findViewById<SwitchCompat>(R.id.toggle_labels_switch)
        labelsToggle.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            showLabels = isChecked
            if (isChecked) {
                for (tabLayout in tabLayouts!!) {
                    setLabelVisibility(tabLayout, TabLayout.TAB_LABEL_VISIBILITY_LABELED)
                }
            } else {
                for (tabLayout in tabLayouts!!) {
                    setLabelVisibility(tabLayout, TabLayout.TAB_LABEL_VISIBILITY_UNLABELED)
                }
            }
        }

        val tabGravityFillButton = view.findViewById<RadioButton>(R.id.tabs_gravity_fill_button)
        tabGravityFillButton.setOnClickListener { v: View? ->
            setAllTabLayoutGravity(
                TabLayout.GRAVITY_FILL
            )
        }

        val tabGravityCenterButton = view.findViewById<RadioButton>(R.id.tabs_gravity_center_button)
        tabGravityCenterButton.setOnClickListener { v: View? ->
            setAllTabLayoutGravity(
                TabLayout.GRAVITY_CENTER
            )
        }

        val tabAnimationModeLinearButton = view.findViewById<RadioButton>(R.id.tabs_animation_mode_linear_button)
        tabAnimationModeLinearButton.setOnClickListener { v: View? ->
            setAllTabAnimationModes(
                TabLayout.INDICATOR_ANIMATION_MODE_LINEAR
            )
        }

        val tabsAnimationModeElasticButton = view.findViewById<RadioButton>(R.id.tabs_animation_mode_elastic_button)
        tabsAnimationModeElasticButton.setOnClickListener { v: View? ->
            setAllTabAnimationModes(
                TabLayout.INDICATOR_ANIMATION_MODE_ELASTIC
            )
        }

        val tabsAnimationModeFadeButton = view.findViewById<RadioButton>(R.id.tabs_animation_mode_fade_button)
        tabsAnimationModeFadeButton.setOnClickListener { v: View? ->
            setAllTabAnimationModes(
                TabLayout.INDICATOR_ANIMATION_MODE_FADE
            )
        }

        val inlineToggle = view.findViewById<SwitchCompat>(R.id.toggle_inline_switch)
        inlineToggle.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            setAllTabLayoutInline(
                isChecked
            )
        }

        val fullWidthToggle = view.findViewById<SwitchCompat>(R.id.toggle_full_width_switch)
        fullWidthToggle.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            setAllTabLayoutFullWidthIndicators(
                isChecked
            )
        }

        val selectedIndicatorSpinner = view.findViewById<View>(R.id.selector_spinner) as Spinner
        val adapter = ArrayAdapter.createFromResource(
            selectedIndicatorSpinner.context,
            getSelectedIndicatorDrawableTitles(),
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectedIndicatorSpinner.adapter = adapter
        selectedIndicatorSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                    setAllTabLayoutSelectedIndicators(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    setAllTabLayoutSelectedIndicators(0)
                }
            }
        return view
    }

    @ArrayRes
    protected fun getSelectedIndicatorDrawableTitles(): Int {
        return R.array.cat_tabs_selected_indicator_drawable_titles
    }

    @ArrayRes
    protected fun getSelectedIndicatorDrawables(): Int {
        return R.array.cat_tabs_selected_indicator_drawables
    }

    @ArrayRes
    protected fun getSelectedIndicatorDrawableGravities(): Int {
        return R.array.cat_tabs_selected_indicator_drawable_gravities
    }

    private fun setupViewPager() {
        pager!!.adapter = TabsPagerAdapter(childFragmentManager, requireContext(), TAB_COUNT)
        for (tabLayout in tabLayouts!!) {
            tabLayout.setupWithViewPager(pager)
        }
    }

    private fun setAllTabLayoutIcons(@DrawableRes iconResId: Int) {
        for (tabLayout in tabLayouts!!) {
            setTabLayoutIcons(tabLayout, iconResId)
        }
    }

    private fun setTabLayoutIcons(tabLayout: TabLayout, @DrawableRes iconResId: Int) {
        for (i in 0 until tabLayout.tabCount) {
            if (showIcons) {
                tabLayout.getTabAt(i)!!.setIcon(iconResId)
            } else {
                tabLayout.getTabAt(i)!!.setIcon(null)
            }
        }
    }

    private fun setAllTabLayoutText(@StringRes stringResId: Int) {
        for (tabLayout in tabLayouts!!) {
            setTabLayoutText(tabLayout, stringResId)
        }
    }

    private fun setTabLayoutText(tabLayout: TabLayout, @StringRes stringResId: Int) {
        for (i in 0 until tabLayout.tabCount) {
            // Convert tab index (zero-based) to readable tab label starting at 1.
            tabLayout.getTabAt(i)!!.text = resources.getString(stringResId, i + 1)
        }
    }

    private fun setAllTabLayoutBadges() {
        for (tabLayout in tabLayouts!!) {
            setupBadging(tabLayout)
            tabLayout.addOnTabSelectedListener(
                object : OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        tab.removeBadge()
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {}
                    override fun onTabReselected(tab: TabLayout.Tab) {
                        tab.removeBadge()
                    }
                })
        }
    }

    private fun setupBadging(tabLayout: TabLayout) {
        var badgeDrawable = tabLayout.getTabAt(0)!!.orCreateBadge
        badgeDrawable.isVisible = true
        badgeDrawable.number = 1
        badgeDrawable = tabLayout.getTabAt(1)!!.orCreateBadge
        badgeDrawable.isVisible = true
        badgeDrawable.number = 88
        badgeDrawable = tabLayout.getTabAt(2)!!.orCreateBadge
        badgeDrawable.isVisible = true
        badgeDrawable.number = 999
    }

    private fun setLabelVisibility(tabLayout: TabLayout, @TabLayout.LabelVisibility mode: Int) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)!!.tabLabelVisibility = mode
        }
    }

    private fun setAllTabLayoutGravity(gravity: Int) {
        for (tabLayout in tabLayouts!!) {
            tabLayout.tabGravity = gravity
        }
    }

    private fun setAllTabAnimationModes(mode: Int) {
        for (tabLayout in tabLayouts!!) {
            tabLayout.tabIndicatorAnimationMode = mode
        }
    }

    private fun setAllTabLayoutInline(inline: Boolean) {
        for (tabLayout in tabLayouts!!) {
            tabLayout.isInlineLabel = inline
        }
    }

    private fun setAllTabLayoutFullWidthIndicators(fullWidth: Boolean) {
        for (tabLayout in tabLayouts!!) {
            tabLayout.isTabIndicatorFullWidth = fullWidth
        }
    }

    private fun setAllTabLayoutSelectedIndicators(position: Int) {
        val drawables = resources.obtainTypedArray(getSelectedIndicatorDrawables())
        @DrawableRes val drawableResId = drawables.getResourceId(position, 0)
        drawables.recycle()
        val drawableGravities = resources.obtainTypedArray(getSelectedIndicatorDrawableGravities())
        val drawableGravity = drawableGravities.getInt(position, 0)
        drawableGravities.recycle()
        for (tabLayout in tabLayouts!!) {
            tabLayout.setSelectedTabIndicator(drawableResId)
            tabLayout.setSelectedTabIndicatorGravity(drawableGravity)
        }
    }
}
