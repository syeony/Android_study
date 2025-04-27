package com.ssafy.materialdesign.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R

open class DialogFragment : BaseFragment() {

    override fun onCreateFragmentView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_dialog, viewGroup, false)
        val dialogLaunchersLayout = view.findViewById<LinearLayout>(R.id.dialog_launcher_buttons_layout)
        val choices = arrayOf<CharSequence>("Choice1", "Choice2", "Choice3")
        val choicesInitial = booleanArrayOf(false, true, false)
        val multiLineMessage = StringBuilder()
        val line = resources.getString(R.string.line)
        for (i in 0..99) {
            multiLineMessage.append(line).append(i).append("\n")
        }
        val positiveText = resources.getString(R.string.positive)
        val negativeText = resources.getString(R.string.negative)
        val title = resources.getString(R.string.title)
        val message = resources.getString(R.string.message)
        val longMessage = resources.getString(R.string.long_message)

        // AppCompat title, message, 3 actions
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.app_compat_alert_dialog
        ) { v: View? ->
            AlertDialog.Builder(
                requireContext()
            )
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .show()
        }

        // message, 2 actions
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.message_2_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(message)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .show()
        }

        // long message, 2 actions
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.long_message_2_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(longMessage)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .show()
        }

        // title, 2 actions
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_2_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .show()
        }

        // title, message, 3 actions (long)
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_message_3_long_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(resources.getString(R.string.long_positive), null)
                .setNegativeButton(resources.getString(R.string.long_negative), null)
                .setNeutralButton(resources.getString(R.string.long_neutral), null)
                .show()
        }

        // long title, message, 1 action (too long)
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.long_title_message_too_long_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.long_title))
                .setMessage(message)
                .setPositiveButton(resources.getString(R.string.too_long_positive), null)
                .setNegativeButton(resources.getString(R.string.too_long_negative), null)
                .setNeutralButton(resources.getString(R.string.too_long_neutral), null)
                .show()
        }

        // icon, title, message, 2 actions
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.icon_title_message_2_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .setIcon(R.drawable.ic_dialogs_24px)
                .show()
        }

        // icon, title, message, 2 actions (centered)
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.icon_title_message_2_actions_centered
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext(), getCenteredTitleThemeOverlay())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .setIcon(R.drawable.ic_dialogs_24px)
                .show()
        }

        // edit text
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.edit_text
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setView(R.layout.edit_text)
                .setPositiveButton(
                    positiveText,
                    DialogInterface.OnClickListener { dialog, which ->
                        val input =
                            (dialog as AlertDialog).findViewById<TextView>(
                                android.R.id.text1
                            )
                        Toast.makeText(context, input!!.text, Toast.LENGTH_LONG)
                            .show()
                    })
                .setNegativeButton(negativeText, null)
                .show()
        }

        // title, auto-action choice dialog
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_choices_as_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setPositiveButton(positiveText, null)
                .setItems(choices, null)
                .show()
        }

        // title, checkboxes, 2 actions dialog
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_checkboxes_2_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setPositiveButton(
                    positiveText
                ) { dialog: DialogInterface, which: Int ->
                    val checkedItemPositions =
                        (dialog as AlertDialog).listView
                            .checkedItemPositions
                    val result: MutableList<CharSequence> =
                        ArrayList()
                    for (i in choices.indices) {
                        if (checkedItemPositions[i]) {
                            result.add(choices[i])
                        }
                    }
                    Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show()
                }
                .setNegativeButton(negativeText, null)
                .setMultiChoiceItems(choices, choicesInitial, null)
                .show()
        }

        // title, radiobutton, 2 actions dialog
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_radiobuttons_2_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setPositiveButton(
                    positiveText
                ) { dialog: DialogInterface, which: Int ->
                    val checkedItemPosition =
                        (dialog as AlertDialog).listView
                            .checkedItemPosition
                    if (checkedItemPosition != AdapterView.INVALID_POSITION) {
                        Toast.makeText(
                            context, choices[checkedItemPosition], Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
                .setNegativeButton(negativeText, null)
                .setSingleChoiceItems(choices, 1, null)
                .show()
        }

        // title, custom view, actions dialog
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_slider_2_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .setView(R.layout.seekbar_layout)
                .show()
        }

        // title, scrolling long view, actions dialog
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_scrolling_2_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(multiLineMessage.toString())
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .show()
        }

        // scrolling view
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_scrolling
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(multiLineMessage.toString())
                .show()
        }

        // title, short buttons
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.title_2_short_actions
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setPositiveButton(R.string.short_text_1, null)
                .setNegativeButton(R.string.short_text_2, null)
                .show()
        }

        // icon, title, message, 2 full width actions
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.icon_title_message_2_actions_fullwidth_buttons
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext(), getDialogFullWidthButtonsStyle())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .setIcon(R.drawable.ic_dialogs_24px)
                .show()
        }

        // icon, title, message, 2 full width actions (centered)
        addDialogLauncher(
            dialogLaunchersLayout,
            R.string.icon_title_message_2_actions_centered_fullwidth_buttons
        ) { v: View? ->
            MaterialAlertDialogBuilder(requireContext(), getCenteredDialogFullWidthButtonsStyle())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, null)
                .setNegativeButton(negativeText, null)
                .setIcon(R.drawable.ic_dialogs_24px)
                .show()
        }
        return view
    }

    private fun getCenteredTitleThemeOverlay(): Int {
        return R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
    }

    private fun getDialogFullWidthButtonsStyle(): Int {
        return R.style.ThemeOverlay_Catalog_MaterialAlertDialog_FullWidthButtons
    }

    private fun getCenteredDialogFullWidthButtonsStyle(): Int {
        return R.style.ThemeOverlay_Catalog_MaterialAlertDialog_Centered_FullWidthButtons
    }

    private fun addDialogLauncher(
        viewGroup: ViewGroup,
        @StringRes stringResId: Int,
        clickListener: View.OnClickListener
    ) {
        val dialogLauncherButton = MaterialButton(viewGroup.context)
        dialogLauncherButton.setOnClickListener(clickListener)
        dialogLauncherButton.setText(stringResId)
        viewGroup.addView(dialogLauncherButton)
    }
}
