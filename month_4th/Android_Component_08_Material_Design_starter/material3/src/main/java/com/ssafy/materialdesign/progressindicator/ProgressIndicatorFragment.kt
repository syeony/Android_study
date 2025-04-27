package com.ssafy.materialdesign.progressindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

class ProgressIndicatorFragment : BaseFragment() {


    override fun onCreateFragmentView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_progress_indicator, container, false )
        initialize(view)
        return view
    }

    private fun initialize(view: View) {
        val linearIndicator = view.findViewById<LinearProgressIndicator>(R.id.linear_indicator)
        val circularIndicator = view.findViewById<CircularProgressIndicator>(R.id.circular_indicator)
        val progressInput = view.findViewById<EditText>(R.id.progress_input)
        val updateButton = view.findViewById<Button>(R.id.update_button)
        val determinateSwitch = view.findViewById<MaterialSwitch>(R.id.determinate_mode_switch)

        updateButton.setOnClickListener { v: View? ->
            var progress: Int
            try {
                progress = progressInput.editableText.toString().toInt()
            } catch (e: NumberFormatException) {
                progress = 0
                progressInput.setText("0")
            }
            // indicator를 확정적으로 세팅
            linearIndicator.setProgressCompat(progress, true)
            circularIndicator.setProgressCompat(progress, true)
            determinateSwitch.isChecked = true
        }
        determinateSwitch.setOnCheckedChangeListener { v: CompoundButton?, isChecked: Boolean ->
            linearIndicator.isIndeterminate = !isChecked
            circularIndicator.isIndeterminate = !isChecked
        }
    }
}
