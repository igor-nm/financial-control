package com.igornm.financialcontrol.extension

import com.igornm.financialcontrol.model.Type
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Created by igor on 25/12/17.
 */

private val FORMAT_BRAZILIAN = SimpleDateFormat("dd/MM/yyyy")
private val FORMAT_DATABASE  = SimpleDateFormat("yyyy-mm-dd")

fun String.convertForCalendar() : Calendar
{
    val calendar : Calendar = Calendar.getInstance()
    val date : Date = FORMAT_BRAZILIAN.parse(this)
    calendar.time = date
    return calendar
}

fun String.SQLiteForCalendar() : Calendar
{
    val calendar : Calendar = Calendar.getInstance()
    val date : Date = FORMAT_DATABASE.parse(this)
    calendar.time = date
    return calendar
}

fun String.convertForSQLite() : String
{
    val date = this.split("/")
    return "${date[2]}-${date[1]}-${date[0]}"
}

fun String.convertForBigDecimal() : BigDecimal
{
    if (!this.isEmpty()) {
        return BigDecimal(this)
    }
    return BigDecimal.ZERO
}

fun String.convertForType() : Type
{
    if(this == Type.INCOME.toString())
    {
        return Type.INCOME
    }
    return Type.EXPENSE
}

fun String.limitAtCharacter(character_limit: Int) : String
{
    if(this.length > character_limit)
    {
        val first_character = 0
        return "${this.substring(first_character, character_limit)}..."
    }
    return this
}