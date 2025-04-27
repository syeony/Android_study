package com.ssafy.materialdesign.transition

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialContainerTransform
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.OnBackPressedHandler

private const val TAG = "TransContV_싸피"
class TransitionContainerTransformViewFragment : BaseFragment(), OnBackPressedHandler {


    private var startCard: View? = null
    private var startFab: View? = null

    private var contactCard: View? = null
    private var endView: View? = null
    private var expandedCard: View? = null

    private var root: FrameLayout? = null

    private lateinit var configurationHelper: ContainerTransformConfigurationHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        configurationHelper = ContainerTransformConfigurationHelper()
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setHasOptionsMenu(true)
    }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(
            R.layout.fragment_transition_container_transform_view, viewGroup, false
        )
        root = view.findViewById<FrameLayout>(R.id.root)
        startFab = view.findViewById<View>(R.id.start_fab)
        expandedCard = view.findViewById<View>(R.id.expanded_card)
        contactCard = view.findViewById<View>(R.id.contact_card)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addTransitionableTarget(view, R.id.start_fab)
        addTransitionableTarget(view, R.id.single_line_list_item)
        addTransitionableTarget(view, R.id.vertical_card_item)
        addTransitionableTarget(view, R.id.horizontal_card_item)
        addTransitionableTarget(view, R.id.grid_card_item)
        addTransitionableTarget(view, R.id.grid_tall_card_item)
        addTransitionableTarget(view, R.id.expanded_card)
        addTransitionableTarget(view, R.id.contact_card)
    }

    private fun addTransitionableTarget(view: View, @IdRes id: Int) {
        val target = view.findViewById<View>(id)
        if (target != null) {
            ViewCompat.setTransitionName(target, id.toString())
            // card거나 열린view면 닫기.
            if (id == R.id.expanded_card || id == R.id.contact_card) {
                target.setOnClickListener { endView: View ->
                    showStartView(endView)
                }
            } else {
                target.setOnClickListener { startView: View ->
                    showEndView(startView)
                }
            }
        }
    }
    // view 창 열기
    private fun showEndView(startView: View) {
        //Fab 클릭이면 contactCard 열기. 아니면 view창(expandedCard)열기
        if (startView.id == R.id.start_fab) {
            endView = contactCard
        } else {
            // Save the startView reference as the startCard that triggered the transition in order to
            // know which card to transition into during the return transition.
            startCard = startView
            endView = expandedCard
        }

        // Construct a container transform transition between two views.
        val transition = buildContainerTransform(true)
        transition.startView = startView
        transition.endView = endView

        // Add a single target to stop the container transform from running on both the start
        // and end view.
        transition.addTarget(endView!!)

        // Trigger the container transform transition.
        TransitionManager.beginDelayedTransition(root!!, transition)
        startView.visibility = View.INVISIBLE
        endView!!.visibility = View.VISIBLE
    }

    //팝업된 창 닫고 바탕창 보이기.
    private fun showStartView(endView: View) {
        val startView = if (endView.id == R.id.contact_card) startFab else startCard

        // Construct a container transform transition between two views.
        val transition = buildContainerTransform(false)
        transition.startView = endView
        transition.endView = startView

        // Add a single target to stop the container transform from running on both the start
        // and end view.
        transition.addTarget(startView!!)

        // Trigger the container transform transition.
        TransitionManager.beginDelayedTransition(root!!, transition)
        startView.visibility = View.VISIBLE
        endView.visibility = View.INVISIBLE
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val context = requireContext()
        val transform = MaterialContainerTransform(context, entering)
        transform.scrimColor = Color.TRANSPARENT
        transform.drawingViewId = root!!.id
        configurationHelper.configure(transform, entering)
        return transform
    }

    override fun onBackPressed(): Boolean {
        if (endView != null && endView!!.visibility == View.VISIBLE) {
            showStartView(endView!!)
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.transform_configure_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.configure) {
            configurationHelper.showConfigurationChooser(
                requireContext()
            ) { dialog -> buildContainerTransform(true) }
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
