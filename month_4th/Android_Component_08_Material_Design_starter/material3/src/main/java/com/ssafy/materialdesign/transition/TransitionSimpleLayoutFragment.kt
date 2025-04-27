/*
 * Copyright 2019 The Android Open Source Project
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
package com.ssafy.materialdesign.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment

/** A fragment that displays the provided layout.  */
class TransitionSimpleLayoutFragment : Fragment() {
    private var layoutResId = 0
    private var transitionName: String? = null
    private var transitionNameViewId = 0
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val args = arguments
        if (args != null) {
            layoutResId = args.getInt(KEY_LAYOUT_RES_ID)
            transitionName = args.getString(KEY_TRANSITION_NAME)
            transitionNameViewId = args.getInt(KEY_TRANSITION_NAME_VIEW_ID)
        }
    }

    override fun onCreateView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(layoutResId, viewGroup, false /* attachToRoot */)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        if (transitionName != null) {
            val transitionNameView =
                if (transitionNameViewId == View.NO_ID) view else view.findViewById(
                    transitionNameViewId
                )
            ViewCompat.setTransitionName(transitionNameView, transitionName)
        }
    }

    companion object {
        private const val KEY_LAYOUT_RES_ID = "KEY_LAYOUT_RES_ID"
        private const val KEY_TRANSITION_NAME = "KEY_TRANSITION_NAME"
        private const val KEY_TRANSITION_NAME_VIEW_ID = "KEY_TRANSITION_NAME_VIEW_ID"
        @JvmOverloads
        fun newInstance(
            @LayoutRes layoutResId: Int,
            transitionName: String? = null,
            @IdRes transitionNameViewId: Int = View.NO_ID
        ): TransitionSimpleLayoutFragment {
            val fragment = TransitionSimpleLayoutFragment()
            val args = Bundle()
            args.putInt(KEY_LAYOUT_RES_ID, layoutResId)
            args.putString(KEY_TRANSITION_NAME, transitionName)
            args.putInt(KEY_TRANSITION_NAME_VIEW_ID, transitionNameViewId)
            fragment.arguments = args
            return fragment
        }
    }
}