package com.ssafy.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DialogActivity2 : AppCompatActivity() {
    private val DIALOG_LIST = 1
    private val DIALOG_SINGLE_CHOICE = 2
    private val DIALOG_MULTIPLE_CHOICE = 3
    private val DIALOG_PROGRESS = 4

    private val MAX_PROGRESS = 100

    private lateinit var mProgressDialog: ProgressDialog
    private var mProgress = 0
    private lateinit var mProgressHandler: Handler

    private fun createDialog(id: Int): Dialog? {
        when (id) {
            DIALOG_LIST -> return AlertDialog.Builder(this@DialogActivity2)
                .setTitle(R.string.select_dialog)
                .setItems(
                    R.array.select_dialog_items,
                    DialogInterface.OnClickListener { dialog, which -> /* User clicked so do some stuff */
                        val items = resources.getStringArray(R.array.select_dialog_items)
                        AlertDialog.Builder(this@DialogActivity2)
                            .setMessage("You selected: " + which + " , " + items[which])
                            .show()
                    })
                .create()

            DIALOG_SINGLE_CHOICE -> return AlertDialog.Builder(this@DialogActivity2)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.alert_dialog_single_choice)
                .setSingleChoiceItems(
                    R.array.select_dialog_items2, 0,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked on a radio button do some stuff */ })
                .setPositiveButton(
                    R.string.alert_dialog_ok,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked Yes so do some stuff */ })
                .setNegativeButton(
                    R.string.alert_dialog_cancel,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked No so do some stuff */ })
                .create()

            DIALOG_MULTIPLE_CHOICE -> return AlertDialog.Builder(this@DialogActivity2)
                .setIcon(R.drawable.ic_popup_reminder)
                .setTitle(R.string.alert_dialog_multi_choice)
                .setMultiChoiceItems(
                    R.array.select_dialog_items3,
                    booleanArrayOf(false, true, false, true, false, false, false),
                    OnMultiChoiceClickListener { dialog, whichButton, isChecked -> /* User clicked on a check box do some stuff */ })
                .setPositiveButton(
                    R.string.alert_dialog_ok,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked Yes so do some stuff */ })
                .setNegativeButton(
                    R.string.alert_dialog_cancel,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked No so do some stuff */ })
                .create()

            DIALOG_PROGRESS -> {
                //ProgressBar를 사용하는것으로 권장하고 ProgressDialog는 deprecated
                mProgressDialog = ProgressDialog(this@DialogActivity2)
                mProgressDialog.setIcon(R.drawable.alert_dialog_icon)
                mProgressDialog.setTitle(R.string.select_dialog)
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                mProgressDialog.max = MAX_PROGRESS
                mProgressDialog.setButton(
                    getText(R.string.alert_dialog_hide)
                ) { dialog, whichButton -> /* User clicked Yes so do some stuff */ }
                mProgressDialog.setButton2(
                    getText(R.string.alert_dialog_cancel)
                ) { dialog, whichButton -> /* User clicked No so do some stuff */ }
                return mProgressDialog
            }

        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog2)

        /* Display a list of items */
        val selectButton = findViewById<View>(R.id.select_button) as Button
        selectButton.setOnClickListener { createDialog(DIALOG_LIST)!!.show() }

        /* Display a radio button group */
        val radioButton = findViewById<View>(R.id.radio_button) as Button
        radioButton.setOnClickListener { createDialog(DIALOG_SINGLE_CHOICE)!!.show() }

        /* Display a list of checkboxes */
        val checkBox = findViewById<View>(R.id.checkbox_button) as Button
        checkBox.setOnClickListener { createDialog(DIALOG_MULTIPLE_CHOICE)!!.show() }

        /* Display a custom progress bar */
        val progressButton = findViewById<View>(R.id.progress_button) as Button
        val textView = findViewById<TextView>(R.id.tv_dialog)
        progressButton.setOnClickListener {
            createDialog(DIALOG_PROGRESS)!!.show()
            mProgress = 0
            mProgressDialog.progress = 0
            mProgressHandler.sendEmptyMessage(0)
        }

        mProgressHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (mProgress >= MAX_PROGRESS) {
                    mProgressDialog.dismiss()
                } else {
                    mProgress++
                    textView.text = mProgress.toString()
                    mProgressDialog.incrementProgressBy(1)
                    mProgressHandler.sendEmptyMessageDelayed(0, 100)
                }
            }
        }
    }



}
