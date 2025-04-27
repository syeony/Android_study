/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssafy.materialdesign.tab

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ssafy.materialdesign.R

/** Pager adapter to control displaying of tab item pages in tabs demo for the Catalog app.  */
class TabsPagerAdapter(
    manager: FragmentManager?,
    private val context: Context,
    private val numTabs: Int
) :
    FragmentPagerAdapter(manager!!) {
    override fun getItem(position: Int): Fragment {
        return TabItemContentFragment.newInstance(getReadableTabPosition(position))
    }

    override fun getCount(): Int {
        return numTabs
    }

    override fun getPageTitle(position: Int): CharSequence {
        return String.format(
            context.getString(R.string.cat_tab_item_label), getReadableTabPosition(position)
        )
    }

    /**
     * Convert zero-based numbering of tabs into readable numbering of tabs starting at 1.
     *
     * @param position - Zero-based tab position
     * @return Readable tab position
     */
    private fun getReadableTabPosition(position: Int): Int {
        return position + 1
    }
}