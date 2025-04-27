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

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

/** Fragment to display layout for each tab item in tabs demo for the Catalog app.  */
open class TextFieldControllableFragment : TextFieldBaseFragment() {


    private lateinit var errorText: String

    override fun initTextFieldDemoControls(layoutInflater: LayoutInflater, view: View) {
        super.initTextFieldDemoControls(layoutInflater, view)
        errorText = resources.getString(R.string.cat_textfield_error)

        // Initialize button for toggling the error text visibility.
        val toggleErrorButton = view.findViewById<Button>(R.id.button_toggle_error)
        toggleErrorButton.setOnClickListener { v: View? ->
            if (!textfields.isEmpty() && textfields.get(0).getError() == null) {
                setAllTextFieldsError(errorText)
                toggleErrorButton.text = resources.getString(R.string.cat_textfield_hide_error_text)
                Snackbar.make(v!!, R.string.cat_textfield_show_error_text, Snackbar.LENGTH_SHORT).show()
            } else {
                setAllTextFieldsError(null)
                toggleErrorButton.text = resources.getString(R.string.cat_textfield_show_error_text)
                Snackbar.make(v!!, R.string.cat_textfield_hide_error_text, Snackbar.LENGTH_SHORT).show()
            }
        }

        // Initialize text field for updating the label text.
        val labelTextField = view.findViewById<TextInputLayout>(R.id.text_input_label)
        val labelEditText = labelTextField.editText
        labelEditText!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!checkTextInputIsNull(labelTextField,  /* showError= */true)) {
                    setAllTextFieldsLabel(labelEditText.text.toString())
                    showToast(R.string.cat_textfield_toast_label_text)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        // Initialize text field for updating the error text.
        val textInputError = view.findViewById<TextInputLayout>(R.id.text_input_error)
        val inputErrorEditText = textInputError.editText
        inputErrorEditText!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!checkTextInputIsNull(textInputError,  /* showError= */true)) {
                    updateErrorText(inputErrorEditText.text.toString(), toggleErrorButton)
                    showToast(R.string.cat_textfield_toast_error_text)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        // Initialize text field for updating the helper text.
        val helperTextTextField = view.findViewById<TextInputLayout>(R.id.text_input_helper_text)
        val helperTextEditText = helperTextTextField.editText
        helperTextEditText!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!checkTextInputIsNull(helperTextTextField,  /* showError= */true)) {
                    setAllTextFieldsHelperText(helperTextEditText.text.toString())
                    showToast(R.string.cat_textfield_toast_helper_text)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        // Initialize text field for updating the placeholder text.
        val placeholderTextField = view.findViewById<TextInputLayout>(R.id.text_input_placeholder)
        val placeholderEditText = placeholderTextField.editText
        placeholderEditText!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!checkTextInputIsNull(placeholderTextField,  /* showError= */true)) {
                    setAllTextFieldsPlaceholder(placeholderEditText.text.toString())
                    showToast(R.string.cat_textfield_toast_placeholder_text)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        // Initialize text field for updating the counter max.
        val counterMaxTextField = view.findViewById<TextInputLayout>(R.id.text_input_counter_max)
        val counterEditText = counterMaxTextField.editText
        counterEditText!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!checkTextInputIsNull(counterMaxTextField,  /* showError= */true)) {
                    val length = counterEditText.text.toString().toInt()
                    setAllTextFieldsCounterMax(length)
                    showToast(R.string.cat_textfield_toast_counter_text)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        // Initialize button for updating all customizable fields.
        val updateButton = view.findViewById<Button>(R.id.button_update)
        updateButton.setOnClickListener { v: View? ->
            var updated = false
            if (!checkTextInputIsNull(labelTextField,  /* showError= */false)) {
                setAllTextFieldsLabel(labelEditText.text.toString())
                updated = true
            }
            if (!checkTextInputIsNull(textInputError,  /* showError= */false)) {
                updateErrorText(inputErrorEditText.text.toString(), toggleErrorButton)
                updated = true
            }
            if (!checkTextInputIsNull(helperTextTextField,  /* showError= */false)) {
                setAllTextFieldsHelperText(helperTextEditText.text.toString())
                updated = true
            }
            if (!checkTextInputIsNull(counterMaxTextField,  /* showError= */false)) {
                val length = counterEditText.text.toString().toInt()
                setAllTextFieldsCounterMax(length)
                updated = true
            }
            if (!checkTextInputIsNull(placeholderTextField,  /* showError= */false)) {
                setAllTextFieldsPlaceholder(placeholderEditText.text.toString())
                updated = true
            }
            if (updated) {
                showToast(R.string.cat_textfield_toast_update_button)
            }
        }

        // Initializing switch to toggle between disabling or enabling text fields.
        val enabledSwitch = view.findViewById<MaterialSwitch>(R.id.cat_textfield_enabled_switch)
        enabledSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            for (textfield in textfields) {
                textfield.isEnabled = isChecked
            }
            val messageId =
                if (isChecked) R.string.cat_textfield_label_enabled else R.string.cat_textfield_label_disabled
            Snackbar.make(buttonView!!, messageId, Snackbar.LENGTH_SHORT).show()
        }
    }

    open fun setAllTextFieldsLabel(label: String) {
        for (textfield in textfields) {
            textfield.hint = label
        }
    }

    open fun setAllTextFieldsError(error: String?) {
        var textfieldWithErrorHasFocus = false
        for (textfield in textfields) {
            setErrorIconClickListeners(textfield)
            textfield.error = error
            textfieldWithErrorHasFocus = textfieldWithErrorHasFocus or textfield.hasFocus()
        }
        if (!textfieldWithErrorHasFocus) {
            // Request accessibility focus on the first text field to show an error.
            (textfields[0].parent as ViewGroup).getChildAt(0).requestFocus()
        }
    }

    open fun updateErrorText(errorText: String, toggleErrorButton: Button) {
        this.errorText = errorText
        // if error already showing, call setError again to update its text.
        if (toggleErrorButton.text.toString() == resources.getString(R.string.cat_textfield_hide_error_text)) {
            for (textfield in textfields) {
                setErrorIconClickListeners(textfield)
                textfield.error = errorText
            }
        }
    }

    open fun setErrorIconClickListeners(textfield: TextInputLayout) {
        textfield.setErrorIconOnClickListener { v: View? -> showToast(R.string.cat_textfield_toast_error_icon_click) }
        textfield.setErrorIconOnLongClickListener { v: View? ->
            showToast(R.string.cat_textfield_toast_error_icon_long_click)
            true
        }
    }

    open fun setAllTextFieldsHelperText(helperText: String) {
        for (textfield in textfields) {
            textfield.helperText = helperText
        }
    }

    open fun setAllTextFieldsPlaceholder(placeholder: String) {
        for (textfield in textfields) {
            textfield.placeholderText = placeholder
        }
    }

    open fun setAllTextFieldsCounterMax(length: Int) {
        for (textfield in textfields) {
            textfield.counterMaxLength = length
        }
    }

    open fun checkTextInputIsNull(
        textInputLayout: TextInputLayout,
        showError: Boolean
    ): Boolean {
        if (textInputLayout.editText!!.text == null
            || textInputLayout.editText!!.length() == 0
        ) {
            if (showError) {
                textInputLayout.error =
                    resources.getString(R.string.cat_textfield_null_input_error_text)
            }
            return true
        }
        textInputLayout.error = null
        return false
    }

    open fun showToast(@StringRes messageResId: Int) {
        Toast.makeText(context, messageResId, Toast.LENGTH_LONG).show()
    }

    @LayoutRes
    override fun getTextFieldDemoControlsLayout(): Int {
        return R.layout.textfield_controls
    }
}
