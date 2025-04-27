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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ssafy.materialdesign.R

/** Fragment to display layout for each tab item in tabs demo for the Catalog app.  */
class TabItemContentFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_tab_item_content, viewGroup, false )

        val textView: TextView = view.findViewById<View>(R.id.tab_number_textview) as TextView
        val tabNumber = requireArguments().getInt(TAB_NUMBER, -1)
        textView.text = String.format(requireContext().getString(R.string.cat_tab_item_content),tabNumber)
        return view
    }

    companion object {
        private const val TAB_NUMBER = "tab_number"
        @JvmStatic
        fun newInstance(tabNumber: Int): TabItemContentFragment {
            val fragment = TabItemContentFragment()
            val bundle = Bundle()
            bundle.putInt(TAB_NUMBER, tabNumber)
            fragment.arguments = bundle
            return fragment
        }
    }
}