package com.igornm.financialcontrol.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Created by igor on 25/12/17.
 */

private val FORMAT_BRAZILIAN = SimpleDateFormat("dd/MM/yyyy");

fun Calendar.convertCalendarForString() : String
{
    return FORMAT_BRAZILIAN.format(this.time);
}

fun convertStringForCalendar(dateText : String) : Calendar
{
    val calendar : Calendar = Calendar.getInstance();
    val date : Date = FORMAT_BRAZILIAN.parse(dateText);
    calendar.time = date;
    return calendar;
}