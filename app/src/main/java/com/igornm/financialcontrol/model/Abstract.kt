package com.igornm.financialcontrol.model

import java.math.BigDecimal

/**
 * Created by igor on 26/12/17.
 */

class Abstract(private val transactions: List<Transaction>)
{
    val income get() = sumTotalByType(Type.INCOME);

    val expense get() = sumTotalByType(Type.EXPENSE);

    val total : BigDecimal get() = income.subtract(expense);

    private fun sumTotalByType(type: Type) : BigDecimal
    {
        val sumTransactionByType : Double = transactions
                                                .filter{ it.type == type; }
                                                .sumByDouble{ it.value.toDouble(); };
        return BigDecimal(sumTransactionByType);
    }

}