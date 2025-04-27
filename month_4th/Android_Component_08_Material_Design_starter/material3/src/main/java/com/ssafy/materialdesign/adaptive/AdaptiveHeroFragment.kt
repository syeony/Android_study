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
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.materialdesign.R

/** A Fragment that hosts a hero demo.  */
class AdaptiveHeroFragment : Fragment() {

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View? {
        val view: View = layoutInflater.inflate(R.layout.fragment_adaptive_hero, viewGroup, false)
        val sideContentList: RecyclerView = view.findViewById(R.id.hero_side_content)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        sideContentList.setLayoutManager(layoutManager)
        val adapter = HeroAdapter()
        sideContentList.setAdapter(adapter)
        ViewCompat.setNestedScrollingEnabled(sideContentList,  false)

        // Set up constraint sets.
        val constraintLayout: ConstraintLayout = view.findViewById(R.id.hero_constraint_layout)
        val smallLayout: ConstraintSet = getSmallLayout(constraintLayout)
        val mediumLayout: ConstraintSet = getMediumLayout(smallLayout)
        val largeLayout: ConstraintSet = getLargeLayout(mediumLayout)
        val screenWidth = resources.configuration.screenWidthDp
        if (screenWidth < AdaptiveUtils.MEDIUM_SCREEN_WIDTH_SIZE) {
            smallLayout.applyTo(constraintLayout)
        } else if (screenWidth < AdaptiveUtils.LARGE_SCREEN_WIDTH_SIZE) {
            mediumLayout.applyTo(constraintLayout)
        } else {
            largeLayout.applyTo(constraintLayout)
        }
        return view
    }

    /* Returns the constraint set to be used for the small layout configuration. */
    private fun getSmallLayout(constraintLayout: ConstraintLayout): ConstraintSet {
        val constraintSet = ConstraintSet()
        // Use the constraint set from the constraint layout.
        constraintSet.clone(constraintLayout)
        return constraintSet
    }

    /* Returns the constraint set to be used for the medium layout configuration. */
    private fun getMediumLayout(smallLayout: ConstraintSet): ConstraintSet {
        val marginHorizontal = resources.getDimensionPixelOffset(R.dimen.cat_adaptive_hero_margin)
        val noMargin = resources.getDimensionPixelOffset(R.dimen.cat_adaptive_margin_none)
        val marginHorizontalAdditional = resources
            .getDimensionPixelOffset(R.dimen.cat_adaptive_hero_margin_horizontal_additional)
        val constraintSet = ConstraintSet()
        constraintSet.clone(smallLayout)

        // Main content.
        constraintSet.connect(
            R.id.hero_main_content, ConstraintSet.TOP, R.id.hero_top_content, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            R.id.hero_main_content,
            ConstraintSet.END,
            R.id.hero_side_content_container,
            ConstraintSet.START
        )
        constraintSet.connect(
            R.id.hero_main_content,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.setMargin(R.id.hero_top_content, ConstraintSet.START, noMargin)
        constraintSet.setMargin(R.id.hero_top_content, ConstraintSet.LEFT, noMargin)
        constraintSet.setMargin(
            R.id.hero_top_content,
            ConstraintSet.END,
            marginHorizontalAdditional
        )
        constraintSet.setMargin(
            R.id.hero_top_content,
            ConstraintSet.RIGHT,
            marginHorizontalAdditional
        )

        // Side content.
        constraintSet.connect(
            R.id.hero_side_content_container,
            ConstraintSet.TOP,
            R.id.hero_top_content,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            R.id.hero_side_content_container,
            ConstraintSet.START,
            R.id.hero_main_content,
            ConstraintSet.END
        )
        constraintSet.constrainPercentWidth(R.id.hero_side_content_container, 0.4f)
        constraintSet.setMargin(
            R.id.hero_side_content_container, ConstraintSet.START, marginHorizontal
        )
        constraintSet.setMargin(
            R.id.hero_side_content_container,
            ConstraintSet.LEFT,
            marginHorizontal
        )

        // Add more margin to the right/end of the side content to make sure there is a 24dp margin on
        // the right/end of the side content.
        constraintSet.setMargin(
            R.id.hero_side_content_container, ConstraintSet.RIGHT, marginHorizontalAdditional
        )
        constraintSet.setMargin(
            R.id.hero_side_content_container, ConstraintSet.END, marginHorizontalAdditional
        )
        return constraintSet
    }

    /* Returns the constraint set to be used for the large layout configuration. */
    private fun getLargeLayout(mediumLayout: ConstraintSet): ConstraintSet {
        val noMargin = resources.getDimensionPixelOffset(R.dimen.cat_adaptive_margin_none)
        val marginHorizontal = resources.getDimensionPixelOffset(R.dimen.cat_adaptive_hero_margin)
        val constraintSet = ConstraintSet()
        constraintSet.clone(mediumLayout)
        // Hero container.
        constraintSet.connect(
            R.id.hero_top_content,
            ConstraintSet.END,
            R.id.hero_side_content_container,
            ConstraintSet.START
        )

        // Side content.
        constraintSet.connect(
            R.id.hero_side_content_container,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )

        // Remove the margin from the main content since it no longer is at the right/end side.
        constraintSet.setMargin(R.id.hero_main_content, ConstraintSet.RIGHT, noMargin)
        constraintSet.setMargin(R.id.hero_main_content, ConstraintSet.END, noMargin)
        constraintSet.setMargin(R.id.hero_top_content, ConstraintSet.RIGHT, marginHorizontal)
        constraintSet.setMargin(R.id.hero_top_content, ConstraintSet.END, marginHorizontal)
        return constraintSet
    }

    /** A RecyclerView adapter for the side content list of the hero demo.  */
    private class HeroAdapter: RecyclerView.Adapter<HeroAdapter.HeroViewHolder?>() {
        /** Provides a reference to the views for each data item.  */
        internal class HeroViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
            val view: View = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaptive_hero_item, parent, false)
            return HeroViewHolder(view)
        }

        override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
            // Populate content. Empty for demo purposes.
        }

        override fun getItemCount(): Int{
            return  10
        }
    }
}