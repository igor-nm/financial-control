package com.igornm.financialcontrol.extension

import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * Created by igor on 25/12/17.
 */

private val FORMAT_BRAZILIAN = SimpleDateFormat("dd/MM/yyyy");

fun Calendar.calendarForString() : String
{
    return FORMAT_BRAZILIAN.format(this.time);
}