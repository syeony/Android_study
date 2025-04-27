package com.ssafy.materialdesign.adaptive

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ReactiveGuide
import androidx.core.util.Consumer
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.ssafy.materialdesign.BaseActivity
import com.ssafy.materialdesign.R
import java.util.concurrent.Executor

class AdaptiveListViewActivity : BaseActivity() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var modalNavDrawer: NavigationView
    private lateinit var detailViewContainer: View
    private lateinit var guideline: ReactiveGuide
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fab: FloatingActionButton
    private lateinit var navRail: NavigationRailView
    private lateinit var navDrawer: NavigationView
    private lateinit var navFab: ExtendedFloatingActionButton

    private lateinit var windowInfoTracker: WindowInfoTrackerCallbackAdapter
    private val stateContainer: Consumer<WindowLayoutInfo> = StateContainer()
    private val handler = Handler(Looper.getMainLooper())
    private val executor =
        Executor { command: Runnable? ->
            handler.post {
                handler.post(
                    command!!
                )
            }
        }

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var configuration: Configuration
    private lateinit var fragmentManager: FragmentManager
    private lateinit var listViewFragment: AdaptiveListViewDemoFragment
    private lateinit var detailViewFragment: AdaptiveListViewDetailDemoFragment

    @SuppressLint("MissingInflatedId")
    override fun onCreateActivityView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.activity_adaptive_list_view, viewGroup, false)
        windowInfoTracker = WindowInfoTrackerCallbackAdapter(WindowInfoTracker.getOrCreate(this))
        drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
        constraintLayout = view.findViewById<ConstraintLayout>(R.id.list_view_activity_constraint_layout)
        modalNavDrawer = view.findViewById<NavigationView>(R.id.modal_nav_drawer)
        detailViewContainer = view.findViewById<View>(R.id.list_view_detail_fragment_container)
        guideline = view.findViewById<ReactiveGuide>(R.id.guideline)
        bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
        fab = view.findViewById(R.id.fab)
        navRail = view.findViewById<NavigationRailView>(R.id.nav_rail)
        navDrawer = view.findViewById<NavigationView>(R.id.nav_drawer)
        navFab = view.findViewById<ExtendedFloatingActionButton>(R.id.nav_fab)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuration = resources.configuration
        fragmentManager = supportFragmentManager
        listViewFragment = AdaptiveListViewDemoFragment()
        detailViewFragment = AdaptiveListViewDetailDemoFragment()

        // Update navigation views according to screen width size.
        val screenWidth = configuration.screenWidthDp
        AdaptiveUtils.updateNavigationViewLayout(
            screenWidth, drawerLayout, modalNavDrawer, fab, bottomNav, navRail, navDrawer, navFab
        )

        // Clear backstack to prevent unexpected behaviors when pressing back button.
        val backStrackEntryCount = fragmentManager.backStackEntryCount
        for (entry in 0 until backStrackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

    private fun updatePortraitLayout() {
        val listViewFragmentId: Int = R.id.list_view_fragment_container
        guideline.setGuidelineEnd(0)
        detailViewContainer.visibility = View.GONE
        listViewFragment.setDetailViewContainerId(listViewFragmentId)
        fragmentManager.beginTransaction().replace(listViewFragmentId, listViewFragment).commit()
    }

    private fun updateLandscapeLayout(guidelinePosition: Int, foldWidth: Int) {
        val listViewFragmentId: Int = R.id.list_view_fragment_container
        val detailViewFragmentId: Int = R.id.list_view_detail_fragment_container
        val landscapeLayout = ConstraintSet()
        landscapeLayout.clone(constraintLayout)
        landscapeLayout.setMargin(detailViewFragmentId, ConstraintSet.START, foldWidth)
        landscapeLayout.applyTo(constraintLayout)
        guideline.setGuidelineEnd(guidelinePosition)
        listViewFragment.setDetailViewContainerId(detailViewFragmentId)
        fragmentManager
            .beginTransaction()
            .replace(listViewFragmentId, listViewFragment)
            .replace(detailViewFragmentId, detailViewFragment)
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
            val displayFeatures: List<DisplayFeature> = windowLayoutInfo.displayFeatures
            var hasVerticalFold = false

            // Update layout according to orientation.
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                updatePortraitLayout()
            } else {
                for (displayFeature in displayFeatures) {
                    if (displayFeature is FoldingFeature) {
                        val foldingFeature: FoldingFeature = displayFeature as FoldingFeature
                        val orientation: FoldingFeature.Orientation = foldingFeature.orientation
                        if (orientation.equals(FoldingFeature.Orientation.VERTICAL)) {
                            val foldPosition: Int = foldingFeature.bounds.left
                            val foldWidth: Int = foldingFeature.bounds.right - foldPosition
                            updateLandscapeLayout(foldPosition, foldWidth)
                            hasVerticalFold = true
                        }
                    }
                }
                if (!hasVerticalFold) {
                    updateLandscapeLayout(constraintLayout.getWidth() / 2, 0)
                }
            }
        }
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return false
    }
}
