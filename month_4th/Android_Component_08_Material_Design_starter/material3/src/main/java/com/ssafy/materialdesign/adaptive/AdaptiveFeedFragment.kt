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

/** A Fragment that hosts a feed demo.  */
class AdaptiveFeedFragment : Fragment() {
    private lateinit var fold: ReactiveGuide
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var closedLayout: ConstraintSet

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View =layoutInflater.inflate(R.layout.fragment_adaptive_feed, viewGroup, false)
        fold = view.findViewById<ReactiveGuide>(R.id.fold)
        // Set up content lists.
        val smallContentList: RecyclerView = view.findViewById<RecyclerView>(R.id.small_content_list)
        setUpContentRecyclerView(smallContentList,  /* isSmallContent= */true, 15)
        val largeContentList: RecyclerView = view.findViewById<RecyclerView>(R.id.large_content_list)
        setUpContentRecyclerView(largeContentList,  /* isSmallContent= */false, 5)
        // Set up constraint sets.
        constraintLayout = view.findViewById<ConstraintLayout>(R.id.feed_constraint_layout)
        closedLayout = ConstraintSet()
        closedLayout.clone(constraintLayout)

        return view
    }

    /* Sets up a recycler view with either small or large items list. */
    private fun setUpContentRecyclerView(recyclerView: RecyclerView, isSmallContent: Boolean, itemCount: Int) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val feedAdapter = FeedAdapter(
            if (isSmallContent) R.layout.adaptive_feed_small_item else R.layout.adaptive_feed_large_item,
            itemCount
        )
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setAdapter(feedAdapter)
        ViewCompat.setNestedScrollingEnabled(recyclerView,  /* enabled= */false)
    }

    /* Returns the constraint set to be used for the open layout configuration. */
    private fun getOpenLayout(closedLayout: ConstraintSet, foldWidth: Int): ConstraintSet {
        val marginHorizontal =
            resources.getDimensionPixelOffset(R.dimen.cat_adaptive_margin_horizontal)
        val constraintSet = ConstraintSet()
        constraintSet.clone(closedLayout)
        // Change top button to be on the right of the fold.
        constraintSet.connect(
            R.id.top_button,
            ConstraintSet.START,
            R.id.fold,
            ConstraintSet.END,
            marginHorizontal + foldWidth
        )
        // Change small content list to be on the right of the fold and below top button.
        constraintSet.connect(
            R.id.small_content_list,
            ConstraintSet.START,
            R.id.fold,
            ConstraintSet.END,
            marginHorizontal + foldWidth
        )
        constraintSet.connect(
            R.id.small_content_list, ConstraintSet.TOP, R.id.top_button, ConstraintSet.BOTTOM
        )
        constraintSet.setVisibility(R.id.highlight_content_card, View.GONE)
        constraintSet.setVisibility(R.id.large_content_list, View.VISIBLE)
        return constraintSet
    }

    /**
     * Applies the open layout configuration.
     *
     * @param foldPosition position of the fold
     * @param foldWidth width of the fold if it's a hinge
     */
    fun setOpenLayout(foldPosition: Int, foldWidth: Int) {
        val openLayout: ConstraintSet = getOpenLayout(closedLayout, foldWidth)
        openLayout.applyTo(constraintLayout)
        fold.setGuidelineEnd(foldPosition)
    }

    /* Applies the closed layout configuration. */
    fun setClosedLayout() {
        fold.setGuidelineEnd(0)
        closedLayout.applyTo(constraintLayout)
    }

    /** A RecyclerView adapter for the content lists of the feed.  */
    private class FeedAdapter (private val itemLayout: Int, private val itemCount:Int) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder?>() {

        /** Provides a reference to the views for each data item.  */
        internal class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
            val view: View =
                LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false)
            return FeedViewHolder(view)
        }

        override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
            // Populate content. Empty for demo purposes.
        }
        override fun getItemCount(): Int {
            return itemCount
        }

    }
}