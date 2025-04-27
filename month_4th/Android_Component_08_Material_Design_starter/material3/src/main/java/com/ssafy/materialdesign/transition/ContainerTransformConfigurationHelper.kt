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

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.ssafy.materialdesign.R

/**
 * A helper class which manages all configuration UI presented in [ ].
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class ContainerTransformConfigurationHelper {
    /**
     * Whether or not to a custom container transform should use [ ].
     */
    var isArcMotionEnabled = false
        private set
    /** The enter duration to be used by a custom container transform.  */
    var enterDuration: Long = 0
        private set
    /** The return duration to be used by a custom container transform.  */
    var returnDuration: Long = 0
        private set
    /** The interpolator to be used by a custom container transform.  */
    var interpolator: Interpolator? = null
        private set
    private var fadeModeButtonId = 0
    /** Whether or not the custom transform should draw debugging lines.  */
    var isDrawDebugEnabled = false
        private set

    init {
        setUpDefaultValues()
    }

    /**
     * Show configuration chooser associated with a container transform from [ ].
     */
    fun showConfigurationChooser(
        context: Context,
        onDismissListener: DialogInterface.OnDismissListener?
    ) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(
            createConfigurationBottomSheetView(context, bottomSheetDialog)
        )
        bottomSheetDialog.setOnDismissListener(onDismissListener)
        bottomSheetDialog.show()
    }

    /** Set up the androidx transition according to the config helper's parameters.  */
    fun configure(transform: MaterialContainerTransform, entering: Boolean) {
        val duration = if (entering) enterDuration else returnDuration
        if (duration != NO_DURATION) {
            transform.duration = duration
        }
        if (interpolator != null) {
            transform.interpolator = interpolator
        }
        if (isArcMotionEnabled) {
            transform.setPathMotion(MaterialArcMotion())
        }
        transform.fadeMode = fadeMode
        transform.isDrawDebugEnabled = isDrawDebugEnabled
    }

    /** Set up the platform transition according to the config helper's parameters.  */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun configure(
        transform: com.google.android.material.transition.platform.MaterialContainerTransform,
        entering: Boolean
    ) {
        val duration = if (entering) enterDuration else returnDuration
        if (duration != NO_DURATION) {
            transform.duration = duration
        }
        if (interpolator != null) {
            transform.interpolator = interpolator
        }
        if (isArcMotionEnabled) {
            transform.setPathMotion(
                com.google.android.material.transition.platform.MaterialArcMotion()
            )
        }
        transform.fadeMode = fadeMode
        transform.isDrawDebugEnabled = isDrawDebugEnabled
    }

    /** The fade mode used by a custom container transform.  */
    val fadeMode: Int
        get() = FADE_MODE_MAP.get(fadeModeButtonId)

    private fun setUpDefaultValues() {
        isArcMotionEnabled = false
        enterDuration = NO_DURATION
        returnDuration = NO_DURATION
        interpolator = null
        fadeModeButtonId = R.id.fade_in_button
        isDrawDebugEnabled = false
    }

    /** Create a bottom sheet dialog that displays controls to configure a container transform.  */
    private fun createConfigurationBottomSheetView(
        context: Context,
        dialog: BottomSheetDialog
    ): View {
        val layout: View =
            LayoutInflater.from(context).inflate(R.layout.transition_configuration_layout, null)
        setUpBottomSheetPathMotionButtonGroup(layout)
        setUpBottomSheetEnterDurationSlider(layout)
        setUpBottomSheetReturnDurationSlider(layout)
        setUpBottomSheetInterpolation(layout)
        setUpBottomSheetFadeModeButtonGroup(layout)
        setUpBottomSheetDebugging(layout)
        setUpBottomSheetConfirmationButtons(layout, dialog)
        return layout
    }

    /** Update whether to use arc motion based on the selected radio button  */
    private fun setUpBottomSheetPathMotionButtonGroup(view: View) {
        val toggleGroup: MaterialButtonToggleGroup =
            view.findViewById<MaterialButtonToggleGroup>(R.id.path_motion_button_group)
        if (toggleGroup != null) {
            // Set initial value.
            toggleGroup.check(if (isArcMotionEnabled) R.id.arc_motion_button else R.id.linear_motion_button)
            toggleGroup.addOnButtonCheckedListener(
                MaterialButtonToggleGroup.OnButtonCheckedListener { group: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean ->
                    if (checkedId == R.id.arc_motion_button) {
                        isArcMotionEnabled = isChecked
                    }
                })
        }
    }

    /** Update the fade mode based on the selected radio button  */
    private fun setUpBottomSheetFadeModeButtonGroup(view: View) {
        val toggleGroup: MaterialButtonToggleGroup =
            view.findViewById<MaterialButtonToggleGroup>(R.id.fade_mode_button_group)
        if (toggleGroup != null) {
            // Set initial value.
            toggleGroup.check(fadeModeButtonId)
            toggleGroup.addOnButtonCheckedListener(
                MaterialButtonToggleGroup.OnButtonCheckedListener { group: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean ->
                    if (isChecked) {
                        fadeModeButtonId = checkedId
                    }
                })
        }
    }

    /** Update enter duration and duration text when the slider value changes.  */
    private fun setUpBottomSheetEnterDurationSlider(view: View) {
        setUpBottomSheetDurationSlider(
            view,
            R.id.enter_duration_slider,
            R.id.enter_duration_value,
            enterDuration.toFloat(),
            Slider.OnChangeListener { slider: Slider?, value: Float, fromUser: Boolean ->
                enterDuration = value.toLong()
            })
    }

    /** Update return duration and duration text when the slider value changes.  */
    private fun setUpBottomSheetReturnDurationSlider(view: View) {
        setUpBottomSheetDurationSlider(
            view,
            R.id.return_duration_slider,
            R.id.return_duration_value,
            returnDuration.toFloat(),
            Slider.OnChangeListener { slider: Slider?, value: Float, fromUser: Boolean ->
                returnDuration = value.toLong()
            })
    }

    @SuppressLint("DefaultLocale")
    private fun setUpBottomSheetDurationSlider(
        view: View,
        @IdRes sliderResId: Int,
        @IdRes labelResId: Int,
        duration: Float,
        listener: Slider.OnChangeListener
    ) {
        val durationSlider: Slider = view.findViewById<Slider>(sliderResId)
        val durationValue: TextView = view.findViewById<TextView>(labelResId)
        if (durationSlider != null && durationValue != null) {
            // Set initial value.
            durationSlider.setValue((if (duration != NO_DURATION.toFloat()) duration else 0).toFloat())
            durationValue.setText(String.format(DURATION_FORMAT, durationSlider.getValue()))
            // Update the duration and durationValue's text whenever the slider is slid.
            durationSlider.addOnChangeListener(
                Slider.OnChangeListener { slider: Slider, value: Float, fromUser: Boolean ->
                    listener.onValueChange(slider, value, fromUser)
                    durationValue.setText(String.format(DURATION_FORMAT, value))
                })
        }
    }

    /** Set up interpolation  */
    private fun setUpBottomSheetInterpolation(view: View) {
        val interpolationGroup: RadioGroup =
            view.findViewById<RadioGroup>(R.id.interpolation_radio_group)
        val customContainer: ViewGroup = view.findViewById<ViewGroup>(R.id.custom_curve_container)
        val overshootTensionTextInputLayout: TextInputLayout =
            view.findViewById<TextInputLayout>(R.id.overshoot_tension_text_input_layout)
        val overshootTensionEditText: EditText =
            view.findViewById<EditText>(R.id.overshoot_tension_edit_text)
        val anticipateOvershootTensionTextInputLayout: TextInputLayout =
            view.findViewById<TextInputLayout>(R.id.anticipate_overshoot_tension_text_input_layout)
        val anticipateOvershootTensionEditText: EditText =
            view.findViewById<EditText>(R.id.anticipate_overshoot_tension_edit_text)
        if (interpolationGroup != null && customContainer != null) {
            setTextInputClearOnTextChanged(view.findViewById<TextInputLayout>(R.id.x1_text_input_layout))
            setTextInputClearOnTextChanged(view.findViewById<TextInputLayout>(R.id.x2_text_input_layout))
            setTextInputClearOnTextChanged(view.findViewById<TextInputLayout>(R.id.y1_text_input_layout))
            setTextInputClearOnTextChanged(view.findViewById<TextInputLayout>(R.id.y2_text_input_layout))
            overshootTensionEditText.setText(CustomOvershootInterpolator.DEFAULT_TENSION.toString())
            anticipateOvershootTensionEditText.setText(CustomAnticipateOvershootInterpolator.DEFAULT_TENSION.toString())

            // Check the correct current radio button and fill in custom bezier fields if applicable.
            if (interpolator is FastOutSlowInInterpolator) {
                interpolationGroup.check(R.id.radio_fast_out_slow_in)
            } else if (interpolator is OvershootInterpolator) {
                interpolationGroup.check(R.id.radio_overshoot)
                if (interpolator is CustomOvershootInterpolator) {
                    val customOvershootInterpolator = interpolator as CustomOvershootInterpolator
                    overshootTensionEditText.setText(customOvershootInterpolator.tension.toString())
                }
            } else if (interpolator is AnticipateOvershootInterpolator) {
                interpolationGroup.check(R.id.radio_anticipate_overshoot)
                if (interpolator is CustomAnticipateOvershootInterpolator) {
                    val customAnticipateOvershootInterpolator =
                        interpolator as CustomAnticipateOvershootInterpolator
                    anticipateOvershootTensionEditText.setText(customAnticipateOvershootInterpolator.tension.toString())
                }
            } else if (interpolator is BounceInterpolator) {
                interpolationGroup.check(R.id.radio_bounce)
            } else if (interpolator is CustomCubicBezier) {
                interpolationGroup.check(R.id.radio_custom)
                val currentInterp = interpolator as CustomCubicBezier
                setTextFloat(
                    view.findViewById<EditText>(R.id.x1_edit_text),
                    currentInterp.controlX1
                )
                setTextFloat(
                    view.findViewById<EditText>(R.id.y1_edit_text),
                    currentInterp.controlY1
                )
                setTextFloat(
                    view.findViewById<EditText>(R.id.x2_edit_text),
                    currentInterp.controlX2
                )
                setTextFloat(
                    view.findViewById<EditText>(R.id.y2_edit_text),
                    currentInterp.controlY2
                )
            } else {
                interpolationGroup.check(R.id.radio_default)
            }

            // Show/hide custom text input fields depending on initial checked radio button.
            updateCustomTextFieldsVisibility(
                interpolationGroup.getCheckedRadioButtonId(),
                overshootTensionTextInputLayout,
                anticipateOvershootTensionTextInputLayout,
                customContainer
            )

            // Watch for any changes to selected radio button and update custom text fields visibility.
            // The custom text field values will be captured when the configuration is applied.
            interpolationGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
                    updateCustomTextFieldsVisibility(
                        checkedId,
                        overshootTensionTextInputLayout,
                        anticipateOvershootTensionTextInputLayout,
                        customContainer
                    )
                })
        }
    }

    /** Set up whether or not to draw debugging paint  */
    private fun setUpBottomSheetDebugging(view: View) {
        val debugCheckbox: CheckBox = view.findViewById<CheckBox>(R.id.draw_debug_checkbox)
        if (debugCheckbox != null) {
            debugCheckbox.setChecked(isDrawDebugEnabled)
            debugCheckbox.setOnCheckedChangeListener(
                CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                    isDrawDebugEnabled = isChecked
                })
        }
    }

    /** Set up buttons to apply and validate configuration values and dismiss the bottom sheet  */
    private fun setUpBottomSheetConfirmationButtons(view: View, dialog: BottomSheetDialog) {
        view.findViewById<View>(R.id.apply_button)
            .setOnClickListener { v: View? ->
                // Capture and update interpolation
                val interpolationGroup: RadioGroup =
                    view.findViewById<RadioGroup>(R.id.interpolation_radio_group)
                val checkedRadioButtonId: Int = interpolationGroup.getCheckedRadioButtonId()
                if (checkedRadioButtonId == R.id.radio_custom) {
                    val x1 = getTextFloat(view.findViewById<EditText>(R.id.x1_edit_text))
                    val y1 = getTextFloat(view.findViewById<EditText>(R.id.y1_edit_text))
                    val x2 = getTextFloat(view.findViewById<EditText>(R.id.x2_edit_text))
                    val y2 = getTextFloat(view.findViewById<EditText>(R.id.y2_edit_text))
                    if (areValidCubicBezierControls(view, x1, y1, x2, y2)) {
                        interpolator = CustomCubicBezier(x1!!, y1!!, x2!!, y2!!)
                        dialog.dismiss()
                    }
                } else if (checkedRadioButtonId == R.id.radio_overshoot) {
                    val overshootTensionEditText: EditText =
                        view.findViewById<EditText>(R.id.overshoot_tension_edit_text)
                    val tension = getTextFloat(overshootTensionEditText)
                    interpolator = tension?.let { CustomOvershootInterpolator(it) }
                        ?: CustomOvershootInterpolator()
                    dialog.dismiss()
                } else if (checkedRadioButtonId == R.id.radio_anticipate_overshoot) {
                    val overshootTensionEditText: EditText =
                        view.findViewById<EditText>(R.id.anticipate_overshoot_tension_edit_text)
                    val tension = getTextFloat(overshootTensionEditText)
                    interpolator = tension?.let { CustomAnticipateOvershootInterpolator(it) }
                        ?: CustomAnticipateOvershootInterpolator()
                    dialog.dismiss()
                } else if (checkedRadioButtonId == R.id.radio_bounce) {
                    interpolator = BounceInterpolator()
                    dialog.dismiss()
                } else if (checkedRadioButtonId == R.id.radio_fast_out_slow_in) {
                    interpolator = FastOutSlowInInterpolator()
                    dialog.dismiss()
                } else {
                    interpolator = null
                    dialog.dismiss()
                }
            }
        view.findViewById<View>(R.id.clear_button)
            .setOnClickListener { v: View? ->
                setUpDefaultValues()
                dialog.dismiss()
            }
    }

    /** A custom overshoot interpolator which exposes its tension.  */
    private class CustomOvershootInterpolator @JvmOverloads internal constructor(val tension: Float = DEFAULT_TENSION) :
        OvershootInterpolator(tension) {
        companion object {
            // This is the default tension value in OvershootInterpolator
            const val DEFAULT_TENSION = 2.0f
        }
    }

    /** A custom anticipate overshoot interpolator which exposes its tension.  */
    private class CustomAnticipateOvershootInterpolator @JvmOverloads internal constructor(val tension: Float = DEFAULT_TENSION) :
        AnticipateOvershootInterpolator(tension) {
        companion object {
            // This is the default tension value in AnticipateOvershootInterpolator
            const val DEFAULT_TENSION = 2.0f
        }
    }

    /** A custom cubic bezier interpolator which exposes its control points.  */
    private class CustomCubicBezier internal constructor(
        val controlX1: Float,
        val controlY1: Float,
        val controlX2: Float,
        val controlY2: Float
    ) : Interpolator {
        private val interpolator: Interpolator

        init {
            interpolator = PathInterpolatorCompat.create(controlX1, controlY1, controlX2, controlY2)
        }

        override fun getInterpolation(input: Float): Float {
            return interpolator.getInterpolation(input)
        }

        fun getDescription(context: Context): String {
            return context.getString(
                R.string.cat_transition_config_custom_interpolator_desc,
                controlX1,
                controlY1,
                controlX2,
                controlY2
            )
        }
    }

    companion object {
        private const val CUBIC_CONTROL_FORMAT = "%.3f"
        private const val DURATION_FORMAT = "%.0f"
        private const val NO_DURATION: Long = -1
        private val FADE_MODE_MAP: SparseIntArray = SparseIntArray()

        init {
            FADE_MODE_MAP.append(R.id.fade_in_button, MaterialContainerTransform.FADE_MODE_IN)
            FADE_MODE_MAP.append(R.id.fade_out_button, MaterialContainerTransform.FADE_MODE_OUT)
            FADE_MODE_MAP.append(R.id.fade_cross_button, MaterialContainerTransform.FADE_MODE_CROSS)
            FADE_MODE_MAP.append(
                R.id.fade_through_button,
                MaterialContainerTransform.FADE_MODE_THROUGH
            )
        }

        private fun updateCustomTextFieldsVisibility(
            checkedId: Int,
            overshootTensionTextInputLayout: TextInputLayout,
            anticipateOvershootTensionTextInputLayout: TextInputLayout,
            customContainer: ViewGroup
        ) {
            overshootTensionTextInputLayout.setVisibility(
                if (checkedId == R.id.radio_overshoot) View.VISIBLE else View.GONE
            )
            anticipateOvershootTensionTextInputLayout.setVisibility(
                if (checkedId == R.id.radio_anticipate_overshoot) View.VISIBLE else View.GONE
            )
            customContainer.setVisibility(if (checkedId == R.id.radio_custom) View.VISIBLE else View.GONE)
        }

        @SuppressLint("DefaultLocale")
        private fun setTextFloat(editText: EditText, value: Float) {
            editText.setText(String.format(CUBIC_CONTROL_FORMAT, value))
        }

        private fun getTextFloat(editText: EditText?): Float? {
            if (editText == null) {
                return null
            }
            val text: String = editText.getText().toString()
            return try {
                java.lang.Float.valueOf(text)
            } catch (e: Exception) {
                null
            }
        }

        private fun setTextInputLayoutError(layout: TextInputLayout) {
            layout.setError(" ")
        }

        private fun setTextInputClearOnTextChanged(layout: TextInputLayout) {
            layout
                .editText
                ?.addTextChangedListener(
                    object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            layout.setError(null)
                        }

                        override fun afterTextChanged(s: Editable) {}
                    })
        }

        private fun isValidCubicBezierControlValue(value: Float?): Boolean {
            return value != null && value >= 0 && value <= 1
        }

        private fun areValidCubicBezierControls(
            view: View, x1: Float?, y1: Float?, x2: Float?, y2: Float?
        ): Boolean {
            var isValid = true
            if (!isValidCubicBezierControlValue(x1)) {
                isValid = false
                setTextInputLayoutError(view.findViewById<TextInputLayout>(R.id.x1_text_input_layout))
            }
            if (!isValidCubicBezierControlValue(y1)) {
                isValid = false
                setTextInputLayoutError(view.findViewById<TextInputLayout>(R.id.y1_text_input_layout))
            }
            if (!isValidCubicBezierControlValue(x2)) {
                isValid = false
                setTextInputLayoutError(view.findViewById<TextInputLayout>(R.id.x2_text_input_layout))
            }
            if (!isValidCubicBezierControlValue(y2)) {
                isValid = false
                setTextInputLayoutError(view.findViewById<TextInputLayout>(R.id.y2_text_input_layout))
            }
            return isValid
        }
    }
}