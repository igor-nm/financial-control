package com.igornm.financialcontrol.model

import java.math.BigDecimal

/**
 * Created by igor on 26/12/17.
 */

class Abstract(private val transactions: List<Transaction>)
{
    val recipe get() = sumTotalByType(Type.RECEITA);

    val expense get() = sumTotalByType(Type.DESPESA);

    val total : BigDecimal get() = recipe.subtract(expense);

    private fun sumTotalByType(type: Type) : BigDecimal
    {
        val sumTransactionByType : Double = transactions
                                                .filter{ it.type == type; }
                                                .sumByDouble{ it.value.toDouble(); };
        return BigDecimal(sumTransactionByType);
    }

}