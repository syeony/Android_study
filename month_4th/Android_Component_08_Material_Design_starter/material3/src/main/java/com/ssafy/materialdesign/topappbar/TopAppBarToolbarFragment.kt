package com.ssafy.materialdesign.topappbar

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.annotation.LayoutRes
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class TopAppBarToolbarFragment : BaseFragment() {


    private val NAVIGATION_ICON_RES_ID = R.drawable.ic_close_vd_theme_24px
    private val MENU_RES_ID = R.menu.topappbar_menu

    private val configureViewData = ConfigureViewData()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
    }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view = layoutInflater.inflate(R.layout.fragment_topappbar_toolbar, viewGroup,  false)
        val toolbars: List<MaterialToolbar> = CommonUtil.findViewsWithType(view, MaterialToolbar::class.java)
        for (toolbar in toolbars) {
            initToolbar(view, toolbar)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.topappbar_configure_toolbars_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.configure_toolbars) {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(createConfigureToolbarsView(bottomSheetDialog))
            bottomSheetDialog.show()
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun initToolbar(view: View, toolbar: MaterialToolbar) {
        toolbar.setNavigationIcon(NAVIGATION_ICON_RES_ID)
        toolbar.setNavigationOnClickListener { v: View? ->
            showSnackbar(view,toolbar.subtitle.toString() + " " + toolbar.title)
        }
        toolbar.inflateMenu(MENU_RES_ID)
        toolbar.setOnMenuItemClickListener { menuItem: MenuItem ->
            menuItem.title?.let { showSnackbar(view, it) }
            true
        }
    }

    private fun showSnackbar(view: View, text: CharSequence) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun createConfigureToolbarsView(bottomSheetDialog: BottomSheetDialog): View {
        val configureView: View = LayoutInflater.from(requireContext())
            .inflate(R.layout.topappbar_configure_toolbars, requireView() as ViewGroup, false)

        val configureViewHolder = ConfigureViewHolder(configureView)
        configureViewHolder.titleEditText.setText(configureViewData.titleText)
        configureViewHolder.titleCenteredCheckBox.isChecked = configureViewData.titleCentered
        configureViewHolder.subtitleEditText.setText(configureViewData.subtitleText)
        configureViewHolder.subtitleCenteredCheckBox.isChecked = configureViewData.subtitleCentered
        configureViewHolder.navigationIconCheckBox.isChecked = configureViewData.navigationIcon
        configureViewHolder.menuItemsCheckBox.isChecked = configureViewData.menuItems

        configureView
            .findViewById<View>(R.id.apply_button)
            .setOnClickListener { v: View? ->
                applyToolbarConfigurations(configureViewHolder)
                bottomSheetDialog.dismiss()
            }
        configureView.findViewById<View>(R.id.cancel_button)
            .setOnClickListener { v: View? ->
                bottomSheetDialog.dismiss()
            }
        return configureView
    }

    private fun applyToolbarConfigurations(holder: ConfigureViewHolder) {
        configureViewData.titleText = holder.titleEditText.text
        configureViewData.titleCentered = holder.titleCenteredCheckBox.isChecked
        configureViewData.subtitleText = holder.subtitleEditText.text
        configureViewData.subtitleCentered = holder.subtitleCenteredCheckBox.isChecked
        configureViewData.navigationIcon = holder.navigationIconCheckBox.isChecked
        configureViewData.menuItems = holder.menuItemsCheckBox.isChecked

        val toolbars: List<MaterialToolbar> = CommonUtil.findViewsWithType(requireView(), MaterialToolbar::class.java)
        for (toolbar in toolbars) {
            if (!TextUtils.isEmpty(configureViewData.titleText)) {
                toolbar.title = configureViewData.titleText
            }
            toolbar.isTitleCentered = configureViewData.titleCentered
            if (!TextUtils.isEmpty(configureViewData.subtitleText)) {
                toolbar.subtitle = configureViewData.subtitleText
            }
            toolbar.isSubtitleCentered = configureViewData.subtitleCentered
            if (configureViewData.navigationIcon) {
                toolbar.setNavigationIcon(NAVIGATION_ICON_RES_ID)
            } else {
                toolbar.navigationIcon = null
            }
            toolbar.menu.clear()
            if (configureViewData.menuItems) {
                toolbar.inflateMenu(MENU_RES_ID)
            }
        }
    }

    private class ConfigureViewHolder(view: View) {
        val titleEditText: EditText
        val titleCenteredCheckBox: CheckBox
        val subtitleEditText: EditText
        val subtitleCenteredCheckBox: CheckBox
        val navigationIconCheckBox: CheckBox
        val menuItemsCheckBox: CheckBox

        init {
            titleEditText = view.findViewById<EditText>(R.id.toolbar_title_edittext)
            titleCenteredCheckBox =
                view.findViewById<CheckBox>(R.id.toolbar_title_centered_checkbox)
            subtitleEditText = view.findViewById<EditText>(R.id.toolbar_subtitle_edittext)
            subtitleCenteredCheckBox =
                view.findViewById<CheckBox>(R.id.toolbar_subtitle_centered_checkbox)
            navigationIconCheckBox =
                view.findViewById<CheckBox>(R.id.toolbar_navigation_icon_checkbox)
            menuItemsCheckBox = view.findViewById<CheckBox>(R.id.toolbar_menu_items_checkbox)
        }
    }

    private inner class ConfigureViewData {
        var titleText: CharSequence = ""
        var titleCentered = false
        var subtitleText: CharSequence = ""
        var subtitleCentered = false
        var navigationIcon = true
        var menuItems = true
    }
}
