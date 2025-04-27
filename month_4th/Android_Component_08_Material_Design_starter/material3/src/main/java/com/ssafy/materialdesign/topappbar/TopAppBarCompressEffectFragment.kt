package com.ssafy.materialdesign.topappbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.tabs.TabLayout
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class TopAppBarCompressEffectFragment : BaseFragment() {


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
    }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_topappbar_compress_effect, viewGroup, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)

        val appBarLayout = view.findViewById<AppBarLayout>(R.id.appbarlayout)
        appBarLayout.statusBarForeground = MaterialShapeDrawable.createWithElevationOverlay(requireContext())
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val showHideTabsButton = view.findViewById<ToggleButton>(R.id.show_hide_tabs_button)
        updateTabVisibility(tabs, showHideTabsButton.isChecked)
        showHideTabsButton.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            updateTabVisibility(
                tabs,
                isChecked
            )
        }
        return view
    }

    private fun updateTabVisibility(tabs: TabLayout, show: Boolean) {
        tabs.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.topappbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return CommonUtil.showSnackbar(requireActivity(), item) || super.onOptionsItemSelected(item)
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }
}
