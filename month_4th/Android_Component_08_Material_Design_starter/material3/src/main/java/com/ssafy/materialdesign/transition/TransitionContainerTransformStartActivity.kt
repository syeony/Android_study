package com.ssafy.materialdesign.transition

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.IdRes
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.ssafy.materialdesign.BaseActivity
import com.ssafy.materialdesign.R

class TransitionContainerTransformStartActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // Set up shared element transition and disable overlay so views don't show above system bars
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)

        configurationHelper = ContainerTransformConfigurationHelper()
        addTransitionableTarget(R.id.start_fab)
        addTransitionableTarget(R.id.single_line_list_item)
        addTransitionableTarget(R.id.vertical_card_item)
        addTransitionableTarget(R.id.horizontal_card_item)
        addTransitionableTarget(R.id.grid_card_item)
        addTransitionableTarget(R.id.grid_tall_card_item)
    }

    override fun onCreateActivityView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        return layoutInflater.inflate(R.layout.activity_transition_container_transform_start, viewGroup,  false)
    }

    override fun onDestroy() {
        super.onDestroy()
        configurationHelper = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.transform_configure_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.configure) {
            configurationHelper!!.showConfigurationChooser(this, null)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addTransitionableTarget(@IdRes id: Int) {
        val target: View = findViewById(id)
        target.setOnClickListener { sharedElement: View ->
            startEndActivity(
                sharedElement
            )
        }
    }

    private fun startEndActivity(sharedElement: View) {
        val intent = Intent(this, TransitionContainerTransformEndActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this, sharedElement, "shared_element_end_root"
        )
        startActivity(intent, options.toBundle())
    }

    override fun getActionBarTitle(): String {
        return getString(R.string.cat_transition_container_transform_activity_title)
    }

    override fun shouldShowDefaultActionBar(): Boolean {
        return true
    }

    companion object{
        var configurationHelper: ContainerTransformConfigurationHelper? = null
    }

}
