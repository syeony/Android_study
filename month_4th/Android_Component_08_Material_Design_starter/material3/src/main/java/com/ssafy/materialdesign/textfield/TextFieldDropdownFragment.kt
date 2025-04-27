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
package com.ssafy.materialdesign.textfield

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import com.ssafy.materialdesign.R

/** Fragment to display layout for each tab item in tabs demo for the Catalog app.  */
class TextFieldDropdownFragment : TextFieldControllableFragment() {

    @LayoutRes
    override fun getTextFieldContent(): Int {
        return R.layout.fragment_textfield_dropdown_menu_content
    }

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = super.onCreateFragmentView(layoutInflater, viewGroup, bundle)

        // Initialize button for toggling the leading icon's visibility.
        val toggleLeadingIconButton = view.findViewById<Button>(R.id.button_toggle_leading_icon)
        toggleLeadingIconButton.visibility = View.VISIBLE
        toggleLeadingIconButton.setOnClickListener { v: View? ->
            if (!textfields.isEmpty() && textfields[0].startIconDrawable == null) {
                for (textfield in textfields) {
                    textfield.setStartIconDrawable(R.drawable.ic_search_24px)
                }
                toggleLeadingIconButton.text = resources.getString(R.string.cat_textfield_hide_leading_icon)
            } else {
                for (textfield in textfields) {
                    textfield.startIconDrawable = null
                }
                toggleLeadingIconButton.text = resources.getString(R.string.cat_textfield_show_leading_icon)
            }
        }
        return view
    }
}
