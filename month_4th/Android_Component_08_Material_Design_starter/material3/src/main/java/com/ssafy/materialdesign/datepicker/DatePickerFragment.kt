package com.ssafy.materialdesign.datepicker

import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.core.util.Pair
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.ssafy.materialdesign.BaseFragment
import com.ssafy.materialdesign.R
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

private const val TAG = "DatePickerFragment_싸피"
class DatePickerFragment : BaseFragment() {

    private lateinit var snackbar: Snackbar
    private var today: Long = 0
    private var nextMonth: Long = 0
    private var janThisYear: Long = 0
    private var decThisYear: Long = 0
    private var oneYearForward: Long = 0
    private var todayPair: Pair<Long, Long>? = null
    private var nextMonthPair: Pair<Long, Long>? = null

    private fun getClearedUtc(): Calendar {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        Log.d(TAG, "getClearedUtc: $utc")
        utc.clear()
        Log.d(TAG, "getClearedUtc: $utc")
        return utc
    }

    //calendar에 오늘 시작 시간으로부터 1월, 12월, 내년등 일자 계산
    private fun initSettings() {
        today = MaterialDatePicker.todayInUtcMilliseconds() //오늘 시작시간.UTC milliseconds representing the first moment of today in local timezone.
        // timestamp 삭제한 빈 calendar
        val calendar = getClearedUtc()
        calendar.timeInMillis = today
        //다음달
        calendar.add(Calendar.MONTH, 1)
        nextMonth = calendar.timeInMillis

        //다시 오늘 시작 시간으로부터 1월, 12월 계산(해달월 오늘날짜)
        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.JANUARY
        janThisYear = calendar.timeInMillis

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.DECEMBER
        decThisYear = calendar.timeInMillis

        //다시 오늘 시작 시간으로 부터 내년 계산(내년 오늘)
        // today를 2024.2.29로 하면 내년은 2025.2.28 나옴.
        calendar.timeInMillis = today
        calendar.add(Calendar.YEAR, 1)
        oneYearForward = calendar.timeInMillis

        todayPair = Pair(today, today)
        nextMonthPair = Pair(nextMonth, nextMonth)
    }

    override fun onCreateFragmentView( layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        val view: View = layoutInflater.inflate(R.layout.fragment_date_picker, container, false)

        val root = view.findViewById<LinearLayout>(R.id.picker_launcher_buttons_layout)
        snackbar = Snackbar.make(container!!, R.string.cat_picker_no_action, Snackbar.LENGTH_LONG)

        // default date picker 만들기
        root.findViewById<MaterialButton>(R.id.launch_default_picker).setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker()
            picker.setPositiveButtonText("확인")
            picker.setNegativeButtonText("취소")

            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                snackbar.setText("ok button clicked. ${Date(it)}")
                snackbar.show()
            }
            datePicker.addOnNegativeButtonClickListener {
                snackbar.setText("cancel clicked.")
                snackbar.show()
            }
            datePicker.show(childFragmentManager, picker.toString())
        }

        // custom date picker 만들기
        val launcher = root.findViewById<MaterialButton>(R.id.cat_picker_launch_button)

        // 팝업, 전체화면 theme
        val dialogTheme = resolveOrThrow(requireContext(), R.attr.materialCalendarTheme)
        val fullscreenTheme = resolveOrThrow(requireContext(), R.attr.materialCalendarFullscreenTheme)

        val selectionMode = root.findViewById<RadioGroup>(R.id.cat_picker_date_selector_group)
        val theme = root.findViewById<RadioGroup>(R.id.cat_picker_theme_group)
        val bounds = root.findViewById<RadioGroup>(R.id.cat_picker_bounds_group)
        val validation = root.findViewById<RadioGroup>(R.id.cat_picker_validation_group)
        val title = root.findViewById<RadioGroup>(R.id.cat_picker_title_group)
        val opening = root.findViewById<RadioGroup>(R.id.cat_picker_opening_month_group)
        val selection = root.findViewById<RadioGroup>(R.id.cat_picker_selection_group)
        val inputMode = root.findViewById<RadioGroup>(R.id.cat_picker_input_mode_group)
        val dayViewDecoratorGroup = root.findViewById<RadioGroup>(R.id.cat_picker_day_view_decorator_group)
        val positiveButton = root.findViewById<RadioGroup>(R.id.cat_picker_positive_button_group)
        val negativeButton = root.findViewById<RadioGroup>(R.id.cat_picker_negative_button_group)

        launcher.setOnClickListener { v: View? ->
            initSettings()
            val selectionModeChoice = selectionMode.checkedRadioButtonId
            val themeChoice = theme.checkedRadioButtonId
            val boundsChoice = bounds.checkedRadioButtonId
            val validationChoice = validation.checkedRadioButtonId
            val titleChoice = title.checkedRadioButtonId
            val openingChoice = opening.checkedRadioButtonId
            val selectionChoice = selection.checkedRadioButtonId
            val inputModeChoices = inputMode.checkedRadioButtonId
            val dayViewDecoratorChoice = dayViewDecoratorGroup.checkedRadioButtonId
            val positiveButtonChoice = positiveButton.checkedRadioButtonId
            val negativeButtonChoice = negativeButton.checkedRadioButtonId

            // 다양한 형태의 화면 구성하기. 화면 종류 : 단일날짜 or From To 화면, 팝업시 특정일 지정하기, 달력 or 텍스트
            val builder = setupDateSelectorBuilder(selectionModeChoice, selectionChoice, inputModeChoices)

            //theme 버튼
            if (themeChoice == R.id.cat_picker_theme_dialog) {
                builder.setTheme(dialogTheme)
            } else if (themeChoice == R.id.cat_picker_theme_fullscreen) {
                builder.setTheme(fullscreenTheme)
            } else if (themeChoice == R.id.cat_picker_theme_custom) {
                builder.setTheme(R.style.ThemeOverlay_Catalog_MaterialCalendar_Custom)
            }
            // title setting
            if (titleChoice == R.id.cat_picker_title_custom) {
                builder.setTitleText(R.string.cat_picker_title_custom)
            } else if (titleChoice == R.id.cat_picker_title_with_description) {
                builder.setTheme(R.style.ThemeOverlay_Catalog_MaterialCalendar_WithDescription)
                builder.setTitleText(getTitleWithDescription())
            }
            // 팝업창 확인, 취소 버튼의 text를 원하는 값으로 바꿀수 있다.
            if (positiveButtonChoice == R.id.cat_picker_positive_button_custom) {
                builder.setPositiveButtonText(R.string.cat_picker_positive_button_text)
            }
            if (negativeButtonChoice == R.id.cat_picker_negative_button_custom) {
                builder.setNegativeButtonText(R.string.cat_picker_negative_button_text)
            }
            // 특정일 표시하기. dot or background
            setupDayViewDecorator(builder, dayViewDecoratorChoice)

            // 달력 제약 만들기.
            // 범위지정, 시작일자 지정,
            val constraintsBuilder = setupConstraintsBuilder(boundsChoice, openingChoice, validationChoice)
            try {
                builder.setCalendarConstraints(constraintsBuilder.build())
                val picker = builder.build()
                addSnackBarListeners(picker)
                picker.show(childFragmentManager, picker.toString())
            } catch (e: IllegalArgumentException) {
                snackbar.setText(e.message!!)
                snackbar.show()
            }
        }
        return view
    }

    private fun setupDateSelectorBuilder(selectionModeChoice: Int, selectionChoice: Int, inputModeChoice: Int): MaterialDatePicker.Builder<*> {
        val inputMode =
            if (inputModeChoice == R.id.cat_picker_input_mode_calendar) MaterialDatePicker.INPUT_MODE_CALENDAR else MaterialDatePicker.INPUT_MODE_TEXT
        return if (selectionModeChoice == R.id.cat_picker_date_selector_single) {
            val builder = MaterialDatePicker.Builder.datePicker()
            if (selectionChoice == R.id.cat_picker_selection_today) {
                builder.setSelection(today)
            } else if (selectionChoice == R.id.cat_picker_selection_next_month) {
                builder.setSelection(nextMonth)
            }
            builder.setInputMode(inputMode)
            builder
        } else {
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            if (selectionChoice == R.id.cat_picker_selection_today) {
                builder.setSelection(todayPair)
            } else if (selectionChoice == R.id.cat_picker_selection_next_month) {
                builder.setSelection(nextMonthPair)
            }
            builder.setInputMode(inputMode)
            builder
        }
    }

    private fun setupConstraintsBuilder(
        boundsChoice: Int, openingChoice: Int, validationChoice: Int
    ): CalendarConstraints.Builder {
        val constraintsBuilder = CalendarConstraints.Builder()
        if (boundsChoice == R.id.cat_picker_bounds_this_year) {
            constraintsBuilder.setStart(janThisYear)
            constraintsBuilder.setEnd(decThisYear)
        } else if (boundsChoice == R.id.cat_picker_bounds_one_year_forward) {
            constraintsBuilder.setStart(today)
            constraintsBuilder.setEnd(oneYearForward)
        }
        if (openingChoice == R.id.cat_picker_opening_month_today) {
            constraintsBuilder.setOpenAt(today)
        } else if (openingChoice == R.id.cat_picker_opening_month_next) {
            constraintsBuilder.setOpenAt(nextMonth)
        }
        if (validationChoice == R.id.cat_picker_validation_today_onward) {
            constraintsBuilder.setValidator(DateValidatorPointForward.now())
        } else if (validationChoice == R.id.cat_picker_validation_weekdays) {
            constraintsBuilder.setValidator(DateValidatorWeekdays())
        } else if (validationChoice == R.id.cat_picker_validation_last_two_weeks) {
            val lowerBoundCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            lowerBoundCalendar.add(Calendar.DAY_OF_MONTH, -14)
            val lowerBound = lowerBoundCalendar.timeInMillis
            val validators: MutableList<DateValidator> = ArrayList()
            validators.add(DateValidatorPointForward.from(lowerBound))
            validators.add(DateValidatorPointBackward.now())
            constraintsBuilder.setValidator(CompositeDateValidator.allOf(validators))
        } else if (validationChoice == R.id.cat_picker_validation_multiple_range) {
            val validatorsMultple: MutableList<DateValidator> = ArrayList()
            val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            utc.timeInMillis = today
            utc[Calendar.DATE] = 10
            val pointBackward = DateValidatorPointBackward.before(utc.timeInMillis)
            utc[Calendar.DATE] = 20
            val validatorsComposite: MutableList<DateValidator> = ArrayList()
            val pointForwardComposite = DateValidatorPointForward.from(utc.timeInMillis)
            utc[Calendar.DATE] = 26
            val pointBackwardComposite = DateValidatorPointBackward.before(utc.timeInMillis)
            validatorsComposite.add(pointForwardComposite)
            validatorsComposite.add(pointBackwardComposite)
            val compositeDateValidator = CompositeDateValidator.allOf(validatorsComposite)
            validatorsMultple.add(pointBackward)
            validatorsMultple.add(compositeDateValidator)
            constraintsBuilder.setValidator(CompositeDateValidator.anyOf(validatorsMultple))
        }
        return constraintsBuilder
    }

    // 문자열 일부만 색칠하기
    private fun getTitleWithDescription(): CharSequence {
        val context = requireContext()
        //색칠할 문자열. alarmTimes
        val alarmTimes = context.getString(R.string.cat_picker_title_description_colored)
        val titleAndDescriptionText = context.getString(R.string.cat_picker_title_description_main) + alarmTimes
        val spannable = SpannableString(titleAndDescriptionText)
        //colorPrimary 색깔로 칠함
        val alarmTimesColor = resolveOrThrow(context, R.attr.colorPrimary)
        val spanStart = titleAndDescriptionText.indexOf(alarmTimes)
        val spanEnd = spanStart + alarmTimes.length
        spannable.setSpan(
            ForegroundColorSpan(alarmTimesColor),
            spanStart,
            spanEnd,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(StyleSpan(Typeface.BOLD), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannable
    }

    private fun setupDayViewDecorator(
        builder: MaterialDatePicker.Builder<*>, dayViewDecoratorChoice: Int
    ) {
        if (dayViewDecoratorChoice == R.id.cat_picker_day_view_decorator_dots) {
            builder.setDayViewDecorator(CircleIndicatorDecorator())
        } else if (dayViewDecoratorChoice == R.id.cat_picker_day_view_decorator_highlights) {
            builder.setDayViewDecorator(BackgroundHighlightDecorator())
        }
    }

    // 선언된 theme 중에서 해당 theme가 있으면 리턴.
    private fun resolveOrThrow(context: Context, attributeResId: Int): Int {
        val typedValue = TypedValue()
        if (context.theme.resolveAttribute(attributeResId, typedValue, true)) {
            return typedValue.data
        }
        throw IllegalArgumentException(context.resources.getResourceName(attributeResId))
    }

    private fun addSnackBarListeners(materialCalendarPicker: MaterialDatePicker<*>) {
        materialCalendarPicker.addOnPositiveButtonClickListener { selection: Any? ->
            snackbar.setText(materialCalendarPicker.headerText)
            snackbar.show()
        }
        materialCalendarPicker.addOnNegativeButtonClickListener { dialog: View? ->
            snackbar.setText(R.string.cat_picker_user_clicked_cancel)
            snackbar.show()
        }
        materialCalendarPicker.addOnCancelListener { dialog: DialogInterface? ->
            snackbar.setText(R.string.cat_picker_cancel)
            snackbar.show()
        }
    }
}
