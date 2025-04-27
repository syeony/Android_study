package com.ssafy.materialdesign.timepicker

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val TAG = "TimePickerFragment_싸피"
class TimePickerFragment : BaseFragment() {

    private var hour = 0
    private var minute = 0

    private val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    @TimeFormat
    private var clockFormat = 0
    private var timeInputMode: Int? = null
    private lateinit var textView: TextView

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view = layoutInflater.inflate(R.layout.fragment_time_picker, viewGroup, false) as ViewGroup
        textView = view.findViewById(R.id.timepicker_time)

        val timeFormatToggle = view.findViewById<MaterialButtonToggleGroup>(R.id.time_format_toggle)
        timeFormatToggle.addOnButtonCheckedListener { group: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean ->
            if (isChecked) {
                if (checkedId == R.id.time_format_12h) {
                    clockFormat = TimeFormat.CLOCK_12H
                } else if (checkedId == R.id.time_format_24h) {
                    clockFormat = TimeFormat.CLOCK_24H
                } else if (checkedId == R.id.time_format_system) {
                    val isSystem24Hour =
                        DateFormat.is24HourFormat(context)
                    clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
                } else {
                    Log.d(TAG, "Invalid time format selection: $checkedId")
                }
            }
        }

        val timeInputModeToggle = view.findViewById<MaterialButtonToggleGroup>(R.id.time_input_mode_toggle)
        timeInputModeToggle.addOnButtonCheckedListener { group: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean ->
            if (isChecked) {
                if (checkedId == R.id.time_input_mode_clock) {
                    timeInputMode = MaterialTimePicker.INPUT_MODE_CLOCK
                } else if (checkedId == R.id.time_input_mode_keyboard) {
                    timeInputMode = MaterialTimePicker.INPUT_MODE_KEYBOARD
                } else if (checkedId == R.id.time_input_mode_default) {
                    timeInputMode = null
                } else {
                    Log.d(TAG, "Invalid time input mode selection: $checkedId")
                }
            }
        }

        // system default timepicker
        val frameworkSwitch = view.findViewById<SwitchCompat>(R.id.framework_switch)
        frameworkSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            for (i in 0 until timeInputModeToggle.childCount) {
                val child = timeInputModeToggle.getChildAt(i)
                child.isEnabled = !isChecked
            }
        }

        timeFormatToggle.check(R.id.time_format_system)
        timeInputModeToggle.check(R.id.time_input_mode_default)
        frameworkSwitch.isChecked = false

        val button = view.findViewById<Button>(R.id.timepicker_button)
        button.setOnClickListener { v: View? ->
            if (frameworkSwitch.isChecked) {
                showFrameworkTimepicker() //system default timepicker
                return@setOnClickListener
            }
            // Material Time Picker.
            val materialTimePickerBuilder = MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(hour)
                .setMinute(minute)
            if (timeInputMode != null) {
                materialTimePickerBuilder.setInputMode(timeInputMode!!)
            }

            val materialTimePicker = materialTimePickerBuilder.build()
            materialTimePicker.show(requireActivity().supportFragmentManager, "fragment_tag")
            materialTimePicker.addOnPositiveButtonClickListener { dialog: View? ->
                val newHour = materialTimePicker.hour
                val newMinute = materialTimePicker.minute
                this@TimePickerFragment.onTimeSet(newHour, newMinute)
            }
        }
        return view
    }

    //system default timepicker
    private fun showFrameworkTimepicker() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { view: TimePicker?, hourOfDay: Int, minute: Int ->
                this@TimePickerFragment.onTimeSet(hourOfDay, minute)
            },
            hour,
            minute,
            clockFormat == TimeFormat.CLOCK_24H
        )
        timePickerDialog.show()
    }

    private fun onTimeSet(newHour: Int, newMinute: Int) {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = newHour
        cal[Calendar.MINUTE] = newMinute
        cal.isLenient = false //잘못 입력된 날짜에 대해서 관대하게 처리않고 Tight하게 관리함. 잘못입력시 exception.
        val format = formatter.format(cal.time)
        textView.text = format
        hour = newHour
        minute = newMinute
    }
}
