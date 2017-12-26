package com.igornm.financialcontrol.extension

import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Created by igor on 25/12/17.
 */

private val FORMAT_BRAZILIAN = SimpleDateFormat("dd/MM/yyyy");

fun String.convertStringForCalendar() : Calendar
{
    val calendar : Calendar = Calendar.getInstance();
    val date : Date = FORMAT_BRAZILIAN.parse(this);
    calendar.time = date;
    return calendar;
}

fun String.isValidCoin() : BigDecimal
{
    if (!this.isEmpty()) {
        return BigDecimal(this);
    }
    return BigDecimal.ZERO;
}

fun String.limitAtCharacter(character_limit: Int) : String
{
    if(this.length > character_limit)
    {
        val first_character = 0
        return "${this.substring(first_character, character_limit)}..."
    }
    return this;
}