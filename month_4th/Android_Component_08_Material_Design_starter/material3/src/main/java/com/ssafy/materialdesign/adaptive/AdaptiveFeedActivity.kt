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
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.drawerlayout.widget.DrawerLayout
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.FoldingFeature.Orientation.Companion.VERTICAL
import androidx.window.layout.FoldingFeature.State.Companion.FLAT
import androidx.window.layout.FoldingFeature.State.Companion.HALF_OPENED
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.ssafy.materialdesign.BaseActivity
import com.ssafy.materialdesign.R
import java.util.concurrent.Executor

/** An Activity which hosts the Adaptive feed demo flow.  */
class AdaptiveFeedActivity : BaseActivity() {
    private lateinit var container: View
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var modalNavDrawer: NavigationView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navRail: NavigationRailView
    private lateinit var navDrawer: NavigationView
    private lateinit var navFab: ExtendedFloatingActionButton
    private lateinit var feedFragment: AdaptiveFeedFragment
    private lateinit var windowInfoTracker: WindowInfoTrackerCallbackAdapter
    private val stateContainer: Consumer<WindowLayoutInfo> = StateContainer()
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val executor = Executor { command: Runnable? ->
        handler.post {
            handler.post(
                command!!
            )
        }
    }
    private lateinit var configuration: Configuration
    @SuppressLint("MissingInflatedId")
    override fun onCreateActivityView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View =  layoutInflater.inflate(R.layout.activity_adaptive_feed, viewGroup, false)
        container = view.findViewById<View>(R.id.feed_activity_container)
        drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        modalNavDrawer = view.findViewById<NavigationView>(R.id.modal_nav_drawer)
        windowInfoTracker = WindowInfoTrackerCallbackAdapter(WindowInfoTracker.getOrCreate(this))
        configuration = getResources().getConfiguration()
        bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
        navRail = view.findViewById<NavigationRailView>(R.id.nav_rail)
        navDrawer = view.findViewById<NavigationView>(R.id.nav_drawer)
        navFab = view.findViewById<ExtendedFloatingActionButton>(R.id.nav_fab)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedFragment = AdaptiveFeedFragment()

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
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, feedFragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        windowInfoTracker.addWindowLayoutInfoListener(this, executor, stateContainer)
    }

    override fun onStop() {
        super.onStop()
        windowInfoTracker.removeWindowLayoutInfoListener(stateContainer)
    }

    private inner class StateContainer : Consumer<WindowLayoutInfo> {
        override fun accept(windowLayoutInfo: WindowLayoutInfo) {

            if (configuration.screenWidthDp < AdaptiveUtils.MEDIUM_SCREEN_WIDTH_SIZE) {
                feedFragment.setClosedLayout()
            } else {
                val displayFeatures: List<DisplayFeature> = windowLayoutInfo.displayFeatures
                var isClosed = true
                for (displayFeature in displayFeatures) {
                    if (displayFeature is FoldingFeature) {
                        val foldingFeature: FoldingFeature = displayFeature as FoldingFeature
                        if (foldingFeature.state == HALF_OPENED || foldingFeature.state == FLAT) {
                            val orientation: FoldingFeature.Orientation = foldingFeature.orientation
                            if (orientation == VERTICAL) {
                                val foldPosition: Int = foldingFeature.bounds.left
                                val foldWidth: Int = foldingFeature.bounds.right - foldPosition
                                // Device is open and fold is vertical.
                                feedFragment.setOpenLayout(foldPosition, foldWidth)
                            } else {
                                // Device is open and fold is horizontal.
                                feedFragment.setOpenLayout(container.width / 2, 0)
                            }
                            isClosed = false
                        }
                    }
                }
                if (isClosed) {
                    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        // Device is closed or not foldable and in portrait.
                        feedFragment.setClosedLayout()
                    } else {
                        // Device is closed or not foldable and in landscape.
                        feedFragment.setOpenLayout(container.width / 2, 0)
                    }
                }
            }
        }
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }
}