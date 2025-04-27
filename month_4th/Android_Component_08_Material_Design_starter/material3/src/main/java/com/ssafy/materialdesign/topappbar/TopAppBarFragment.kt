package com.ssafy.materialdesign.topappbar

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil
import com.ssafy.materialdesign.util.CommonUtil.showSnackbar

private const val TAG = "TopAppBarFrag_싸피"
class TopAppBarFragment : BaseFragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var badgeDrawable: BadgeDrawable
    private lateinit var editMenuToggle: MaterialSwitch
    private lateinit var incrementBadgeNumber: MaterialButton

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_topappbar, viewGroup, false)
        toolbar = view.findViewById(R.id.toolbar)
        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbar)
        editMenuToggle = view.findViewById(R.id.cat_topappbar_switch_edit_menu)
        editMenuToggle.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            BadgeUtils.detachBadgeDrawable(
                badgeDrawable,
                toolbar,
                R.id.cat_topappbar_item_favorite
            )
            toolbar.getMenu().findItem(R.id.cat_topappbar_item_edit).isVisible = isChecked
            BadgeUtils.attachBadgeDrawable(
                badgeDrawable,
                toolbar,
                R.id.cat_topappbar_item_favorite
            )
        }
        incrementBadgeNumber = view.findViewById(R.id.cat_topappbar_button_increment_badge)
        incrementBadgeNumber.setOnClickListener { v: View? ->
            badgeDrawable.number = badgeDrawable.number + 1
            badgeDrawable.isVisible = true
        }
        return view
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.topappbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
        badgeDrawable = BadgeDrawable.create(requireContext())
        badgeDrawable.number = 1
        BadgeUtils.attachBadgeDrawable(badgeDrawable, toolbar, R.id.cat_topappbar_item_favorite)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cat_topappbar_item_favorite) {
            badgeDrawable.clearNumber()
            badgeDrawable.isVisible = false
        }
        return showSnackbar(requireActivity(),  item) || super.onOptionsItemSelected(item)
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }


}
