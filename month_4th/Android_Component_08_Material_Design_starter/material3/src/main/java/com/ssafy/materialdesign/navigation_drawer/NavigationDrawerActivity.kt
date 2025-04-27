package com.ssafy.materialdesign.navigation_drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseActivity
import com.ssafy.materialdesign.R

class NavigationDrawerActivity : BaseActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreateActivityView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.activity_navigationdrawer, viewGroup, false )
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer)
        drawerLayout.addDrawerListener(
            ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.cat_navigationdrawer_button_show_content_description,
                R.string.cat_navigationdrawer_button_hide_content_description
            )
        )
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem: MenuItem ->
            Snackbar.make(findViewById(R.id.drawer), "clicked..${menuItem.title}", Snackbar.LENGTH_SHORT).show();
            navigationView.setCheckedItem(menuItem)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        return view
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }

}
