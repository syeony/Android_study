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
import androidx.annotation.LayoutRes
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

/** Fragment to display layout for each tab item in tabs demo for the Catalog app.  */
open class TextFieldBaseFragment : BaseFragment() {


    protected lateinit var textfields: List<TextInputLayout>

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_textfield, viewGroup, false)
        initTextFields(layoutInflater, view)
        initTextFieldDemoControls(layoutInflater, view)
        return view
    }

    private fun initTextFields(layoutInflater: LayoutInflater, view: View) {
        inflateTextFields(layoutInflater, view.findViewById(R.id.content))
        // Add text fields from the content layout before the text fields from the demo controls to
        // allow for modifying the demo text fields without modifying the textfields used for the
        // demo controls.
        addTextFieldsToList(view)
    }

    private fun inflateTextFields(layoutInflater: LayoutInflater, content: ViewGroup) {
        content.addView(layoutInflater.inflate(getTextFieldContent(), content, false))
    }

    open fun initTextFieldDemoControls(layoutInflater: LayoutInflater, view: View) {
        inflateTextFieldDemoControls(layoutInflater, view.findViewById(R.id.content))
    }

    private fun inflateTextFieldDemoControls(layoutInflater: LayoutInflater, content: ViewGroup) {
        @LayoutRes val demoControls = getTextFieldDemoControlsLayout()
        if (demoControls != 0) {
            content.addView(
                layoutInflater.inflate(
                    getTextFieldDemoControlsLayout(),
                    content,
                    false
                )
            )
        }
    }

    private fun addTextFieldsToList(view: View) {
        textfields = CommonUtil.findViewsWithType(view, TextInputLayout::class.java)
    }

    @LayoutRes
    open fun getTextFieldContent(): Int {
        return R.layout.textfield_content
    }

    @LayoutRes
    open fun getTextFieldDemoControlsLayout(): Int {
        return 0
    }
}


