/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssafy.materialdesign.adaptive

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.ssafy.materialdesign.BaseActivity
import com.ssafy.materialdesign.R

/** An Activity which hosts the Adaptive hero demo flow.  */
class AdaptiveHeroActivity : BaseActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var modalNavDrawer: NavigationView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navRail: NavigationRailView
    private lateinit var navDrawer: NavigationView
    private lateinit var navFab: ExtendedFloatingActionButton

    @SuppressLint("MissingInflatedId")
    override fun onCreateActivityView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.activity_adaptive_hero, viewGroup, false)
        drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        modalNavDrawer = view.findViewById<NavigationView>(R.id.modal_nav_drawer)
        bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
        navRail = view.findViewById<NavigationRailView>(R.id.nav_rail)
        navDrawer = view.findViewById<NavigationView>(R.id.nav_drawer)
        navFab = view.findViewById<ExtendedFloatingActionButton>(R.id.nav_fab)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration: Configuration = resources.configuration

        // Update navigation views according to screen width size.
        val screenWidth = configuration.screenWidthDp
        AdaptiveUtils.updateNavigationViewLayout(
            screenWidth,
            drawerLayout,
            modalNavDrawer,
            null,
            bottomNav,
            navRail,
            navDrawer,
            navFab
        )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, AdaptiveHeroFragment())
            .commit()
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }
}