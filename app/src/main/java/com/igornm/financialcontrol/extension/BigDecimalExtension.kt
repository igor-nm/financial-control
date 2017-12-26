package com.igornm.financialcontrol.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

/**
 * Created by igor on 25/12/17.
 */

fun BigDecimal.convertForCoinBrazilian() : String
{
    val formatBrazilian = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"));
    return formatBrazilian
            .format(this)
            .replace("R$", "R$ ")
            .replace("-R$ ", "R$ ");
}