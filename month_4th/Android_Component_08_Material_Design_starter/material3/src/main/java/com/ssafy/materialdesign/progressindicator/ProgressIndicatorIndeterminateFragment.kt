package com.ssafy.materialdesign.progressindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.LayoutRes
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import com.ssafy.materialdesign.util.CommonUtil

class ProgressIndicatorIndeterminateFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_progress_indicator_indeterminate,container,false )
        val content = view.findViewById<ViewGroup>(R.id.content)
        content.addView(layoutInflater.inflate(R.layout.progress_indicator_indeterminate_indicators, content, false), 0)
        initialize(view)
        return view
    }

    /**
     * Updates the linear progress indicator to show the specified progress. This is called every time
     * the "update" button is pressed.
     */
    protected fun updateLinearProgressIndicator(
        linearProgressIndicator: LinearProgressIndicator, progress: Int
    ) {
        linearProgressIndicator.setProgressCompat(progress,  /*animated=*/true)
    }

    /**
     * Updates the circular progress indicator to show the specified progress. This is called every
     * time the "update" button is pressed.
     */
    protected fun updateCircularProgressIndicator(
        circularProgressIndicator: CircularProgressIndicator, progress: Int
    ) {
        circularProgressIndicator.setProgressCompat(progress,  /*animated=*/true)
    }

    private fun initialize(view: View) {
        val linearProgressIndicatorList: List<LinearProgressIndicator> =
            CommonUtil.findViewsWithType(view, LinearProgressIndicator::class.java)
        val circularProgressIndicatorList: List<CircularProgressIndicator> =
            CommonUtil.findViewsWithType(view, CircularProgressIndicator::class.java)
        val progressInput = view.findViewById<EditText>(R.id.progress_input)
        val updateButton = view.findViewById<Button>(R.id.update_button)
        val showButton = view.findViewById<Button>(R.id.show_button)
        val hideButton = view.findViewById<Button>(R.id.hide_button)
        updateButton.setOnClickListener { v: View? ->
            val progress: Int
            progress = try {
                progressInput.editableText.toString().toInt()
            } catch (e: NumberFormatException) {
                0
            }
            for (indicator in linearProgressIndicatorList) {
                updateLinearProgressIndicator(indicator, progress)
            }
            for (indicator in circularProgressIndicatorList) {
                updateCircularProgressIndicator(indicator, progress)
            }
        }
        showButton.setOnClickListener { v: View? ->
            for (indicator in linearProgressIndicatorList) {
                indicator.isIndeterminate = true
                indicator.show()
            }
            for (indicator in circularProgressIndicatorList) {
                indicator.isIndeterminate = true
                indicator.show()
            }
        }
        hideButton.setOnClickListener { v: View? ->
            for (indicator in linearProgressIndicatorList) {
                indicator.hide()
            }
            for (indicator in circularProgressIndicatorList) {
                indicator.hide()
            }
        }
    }
}
