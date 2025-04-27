package com.ssafy.materialdesign.datepicker

import android.os.Parcel
import android.os.Parcelable.Creator
import com.google.android.material.datepicker.CalendarConstraints
import java.util.Arrays
import java.util.Calendar
import java.util.TimeZone

class DateValidatorWeekdays() : CalendarConstraints.DateValidator {

    private val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    @JvmField
    val CREATOR: Creator<DateValidatorWeekdays> = object : Creator<DateValidatorWeekdays> {
        override fun createFromParcel(source: Parcel): DateValidatorWeekdays {
            return DateValidatorWeekdays()
        }

        override fun newArray(size: Int): Array<DateValidatorWeekdays?> {
            return arrayOfNulls(size)
        }
    }

    override fun isValid(date: Long): Boolean {
        utc.timeInMillis = date
        val dayOfWeek = utc[Calendar.DAY_OF_WEEK]
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        return if (o !is DateValidatorWeekdays) {
            false
        } else true
    }

    override fun hashCode(): Int {
        val hashedFields = arrayOf<Any>()
        return Arrays.hashCode(hashedFields)
    }
}
