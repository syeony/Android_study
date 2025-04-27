package com.ssafy.materialdesign.transition

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.OnBackPressedHandler

class TransitionContainerTransformFragment : BaseFragment(), OnBackPressedHandler {

    private val END_FRAGMENT_TAG = "END_FRAGMENT_TAG"

    private lateinit var configurationHelper: ContainerTransformConfigurationHelper

    private val holdTransition = Hold()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        configurationHelper = ContainerTransformConfigurationHelper()
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
    }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        return layoutInflater.inflate(R.layout.fragment_transition_container_transform, viewGroup, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager
            .registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentViewCreated(
                        fragmentManager: FragmentManager,
                        fragment: Fragment,
                        view: View,
                        bundle: Bundle?
                    ) {
                        addTransitionableTarget(view, R.id.start_fab)
                        addTransitionableTarget(view, R.id.single_line_list_item)
                        addTransitionableTarget(view, R.id.vertical_card_item)
                        addTransitionableTarget(view, R.id.horizontal_card_item)
                        addTransitionableTarget(view, R.id.grid_card_item)
                        addTransitionableTarget(view, R.id.grid_tall_card_item)
                    }
                },
                true
            )
        showStartFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.transform_configure_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.configure) {
            configurationHelper.showConfigurationChooser(
                requireContext()
            ) { dialog ->
                // Apply the latest transition configurations to the end fragment that may already exist
                val endFragment = childFragmentManager.findFragmentByTag(END_FRAGMENT_TAG)
                endFragment?.let { configureTransitions(it) }
            }
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun addTransitionableTarget(view: View, @IdRes id: Int) {
        val target = view.findViewById<View>(id)
        if (target != null) {
            ViewCompat.setTransitionName(target, id.toString())
            target.setOnClickListener { sharedElement: View ->
                showEndFragment(
                    sharedElement
                )
            }
        }
    }

    private fun showStartFragment() {
        val fragment: TransitionSimpleLayoutFragment = TransitionSimpleLayoutFragment.newInstance(
            R.layout.transition_container_transform_start_fragment,
            "shared_element_fab",
            R.id.start_fab
        )

        // Add root view as target for the Hold so that the entire view hierarchy is held in place as
        // one instead of each child view individually. Helps keep shadows during the transition.
        holdTransition.addTarget(R.id.start_root)
        fragment.setExitTransition(holdTransition)
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("ContainerTransformFragment::start")
            .commit()
    }

    private fun showEndFragment(sharedElement: View) {
        val transitionName = "shared_element_end_root"
        val fragment: Fragment = TransitionSimpleLayoutFragment.newInstance(
            R.layout.transition_container_transform_end_fragment, transitionName
        )
        configureTransitions(fragment)
        childFragmentManager
            .beginTransaction()
            .addSharedElement(sharedElement, transitionName)
            .replace(R.id.fragment_container, fragment, END_FRAGMENT_TAG)
            .addToBackStack("ContainerTransformFragment::end")
            .commit()
    }

    private fun configureTransitions(fragment: Fragment) {
        // For all 3 container layer colors, use colorSurface since this transform can be configured
        // using any fade mode and some of the start views don't have a background and the end view
        // doesn't have a background.
        val colorSurface = MaterialColors.getColor(requireView(), R.attr.colorSurface)
        val enterContainerTransform = buildContainerTransform(true)
        enterContainerTransform.setAllContainerColors(colorSurface)
        fragment.sharedElementEnterTransition = enterContainerTransform
        holdTransition.duration = enterContainerTransform.duration
        val returnContainerTransform = buildContainerTransform(false)
        returnContainerTransform.setAllContainerColors(colorSurface)
        fragment.sharedElementReturnTransition = returnContainerTransform
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val context = requireContext()
        val transform = MaterialContainerTransform(context, entering)
        transform.setDrawingViewId(if (entering) R.id.end_root else R.id.start_root)
        configurationHelper.configure(transform, entering)
        return transform
    }

    override fun onBackPressed(): Boolean {
        if (requireView().findViewById<View>(R.id.end_root) != null) {
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }
}
