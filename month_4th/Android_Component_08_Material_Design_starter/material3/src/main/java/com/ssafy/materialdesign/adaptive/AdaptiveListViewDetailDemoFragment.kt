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
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.ssafy.materialdesign.R

/** A Fragment that displays an email's details.  */
class AdaptiveListViewDetailDemoFragment : Fragment() {
    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_adaptive_list_view_detail, viewGroup, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        val emailId = emailId
        val emailTitle: TextView = view.findViewById<TextView>(R.id.email_title)
        emailTitle.append(" " + (emailId + 1))
        // Set transition name that matches the list item to be transitioned from for the shared element
        // transition.
        val container = view.findViewById<View>(R.id.list_view_detail_container)
        ViewCompat.setTransitionName(container, emailTitle.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        updateEmailSelected(false)
    }

    override fun onStart() {
        super.onStart()
        updateEmailSelected(true)
    }

    private fun updateEmailSelected(selected: Boolean) {
        val email: AdaptiveListViewDemoFragment.EmailData.Email = AdaptiveListViewDemoFragment.EmailData.getEmailById(emailId)
        email.isSelected = selected
    }

    private val emailId: Long
        get() {
            var emailId = 0L
            if (arguments != null) {
                emailId = requireArguments().getLong(EMAIL_ID_KEY, 0L)
            }
            return emailId
        }

    companion object {
        const val TAG = "AdaptiveListViewDetailDemoFragment"
        private const val EMAIL_ID_KEY = "email_id_key"
        fun newInstance(emailId: Long): AdaptiveListViewDetailDemoFragment {
            val fragment = AdaptiveListViewDetailDemoFragment()
            val bundle = Bundle()
            bundle.putLong(EMAIL_ID_KEY, emailId)
            fragment.arguments = bundle
            return fragment
        }
    }
}