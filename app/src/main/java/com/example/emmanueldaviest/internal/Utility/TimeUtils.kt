package com.emmanueldavies.flixbus.domain.interactor

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.GERMAN)

    fun getTime(dateTime: Long, timeZone: String): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateTime
        calendar.timeZone = TimeZone.getTimeZone(timeZone)
        return timeFormat.format(calendar.time)
    }

}