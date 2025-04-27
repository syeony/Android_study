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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ReactiveGuide
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.materialdesign.R

/** A Fragment that hosts a supporting panel demo.  */
class AdaptiveSupportingPanelFragment : Fragment() {
    private lateinit var  fragmentContainer: ConstraintLayout
    private lateinit var  portraitLayout: ConstraintSet
    private lateinit var  landscapeLayout: ConstraintSet
    private lateinit var  guideline: ReactiveGuide
    
    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {

        val view: View = layoutInflater.inflate(R.layout.fragment_adaptive_supporting_panel,viewGroup,false)

        guideline = view.findViewById<ReactiveGuide>(R.id.horizontal_fold)
        val supportingPanelList: RecyclerView =
            view.findViewById<RecyclerView>(R.id.supporting_panel_side_container)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        supportingPanelList.setLayoutManager(layoutManager)
        val adapter = PanelAdapter()
        supportingPanelList.setAdapter(adapter)
        ViewCompat.setNestedScrollingEnabled(supportingPanelList,  false)

        // Set up constraint sets.
        fragmentContainer = view.findViewById<ConstraintLayout>(R.id.supporting_panel_container)
        portraitLayout = ConstraintSet()
        portraitLayout.clone(fragmentContainer)
        landscapeLayout = getLandscapeLayout(fragmentContainer)

        return view
    }

    /* Applies the portrait layout configuration. */
    fun updatePortraitLayout() {
        portraitLayout.applyTo(fragmentContainer)
    }

    /* Applies the landscape layout configuration. */
    fun updateLandscapeLayout() {
        landscapeLayout.applyTo(fragmentContainer)
    }

    /**
     * Applies the table top layout configuration.
     *
     * @param foldPosition position of the fold
     * @param foldWidth width of the fold if it's a hinge
     */
    fun updateTableTopLayout(foldPosition: Int, foldWidth: Int) {
        val tableTopLayout: ConstraintSet = getTableTopLayout(portraitLayout, foldWidth)
        tableTopLayout.applyTo(fragmentContainer)
        guideline.setGuidelineBegin(foldPosition)
    }

    /* Returns the constraint set to be used for the landscape layout configuration. */
    private fun getLandscapeLayout(constraintLayout: ConstraintLayout): ConstraintSet {
        val marginVertical = resources.getDimensionPixelOffset(R.dimen.cat_adaptive_margin_vertical)
        val marginHorizontal =
            resources.getDimensionPixelOffset(R.dimen.cat_adaptive_margin_horizontal)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        // Main content.
        constraintSet.connect(
            R.id.supporting_panel_main_content,
            ConstraintSet.END,
            R.id.supporting_panel_side_container,
            ConstraintSet.START
        )
        constraintSet.connect(
            R.id.supporting_panel_main_content,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.setMargin(
            R.id.supporting_panel_main_content,
            ConstraintSet.TOP,
            marginVertical
        )
        constraintSet.setMargin(
            R.id.supporting_panel_main_content, ConstraintSet.BOTTOM, marginVertical
        )
        constraintSet.setMargin(
            R.id.supporting_panel_main_content, ConstraintSet.END, marginHorizontal
        )
        constraintSet.constrainMinHeight(R.id.supporting_panel_main_content, 0)
        // Supporting panel content.
        constraintSet.connect(
            R.id.supporting_panel_side_container,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            R.id.supporting_panel_side_container,
            ConstraintSet.START,
            R.id.supporting_panel_main_content,
            ConstraintSet.END
        )
        constraintSet.constrainPercentWidth(R.id.supporting_panel_side_container, 0.4f)
        return constraintSet
    }

    /* Returns the constraint set to be used for the table top layout configuration. */
    private fun getTableTopLayout(portraitLayout: ConstraintSet, foldWidth: Int): ConstraintSet {
        val marginVertical = resources.getDimensionPixelOffset(R.dimen.cat_adaptive_margin_vertical)
        val constraintSet = ConstraintSet()
        constraintSet.clone(portraitLayout)
        constraintSet.setVisibility(R.id.horizontal_fold, View.VISIBLE)
        // Main content
        constraintSet.connect(
            R.id.supporting_panel_main_content,
            ConstraintSet.BOTTOM,
            R.id.horizontal_fold,
            ConstraintSet.TOP
        )
        constraintSet.setMargin(
            R.id.supporting_panel_main_content, ConstraintSet.BOTTOM, marginVertical
        )
        constraintSet.constrainMinHeight(R.id.supporting_panel_main_content, 0)

        // Supporting panel content
        constraintSet.connect(
            R.id.supporting_panel_side_container,
            ConstraintSet.TOP,
            R.id.horizontal_fold,
            ConstraintSet.BOTTOM,
            marginVertical + foldWidth
        )
        return constraintSet
    }

    /** A RecyclerView adapter for the side content list of the supporting panel demo.  */
    private class PanelAdapter : RecyclerView.Adapter<PanelAdapter.PanelViewHolder?>() {
        /** Provides a reference to the views for each data item.  */
        internal class PanelViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelViewHolder {
            val view: View = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaptive_supporting_panel_item, parent, false)
            return PanelViewHolder(view)
        }

        override fun onBindViewHolder(holder: PanelViewHolder, position: Int) {
            // Populate content. Empty for demo purposes.
        }

        override fun getItemCount(): Int{
            return 10
        }
    }
}