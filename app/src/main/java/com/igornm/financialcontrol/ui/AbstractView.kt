package com.igornm.financialcontrol.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.extension.convertForCoinBrazilian
import com.igornm.financialcontrol.model.Abstract
import com.igornm.financialcontrol.model.Transaction
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

/**
 * Created by igor on 26/12/17.
 */

class AbstractView(context: Context,
                   transactions: List<Transaction>,
                   private val view: View)
{
    private val abstract     = Abstract(transactions)
    private val incomeColor  = ContextCompat.getColor(context, R.color.receita);
    private val expenseColor = ContextCompat.getColor(context, R.color.despesa);

    fun update()
    {
        addIncome();
        addExpense();
        addTotal();
    }

    private fun addIncome()
    {
        val recipeTotal = abstract.income;

        with(view.resumo_card_receita)
        {
            setTextColor(incomeColor);
            text = recipeTotal.convertForCoinBrazilian();
        }
    }

    private fun addExpense()
    {
        val expenseTotal = abstract.expense;

        with(view.resumo_card_despesa)
        {
            setTextColor(expenseColor);
            text = expenseTotal.convertForCoinBrazilian();
        }
    }

    private fun addTotal()
    {
        val total = abstract.total;
        val color = getColorTotal(total);

        with(view.resumo_card_total)
        {
            setTextColor(color);
            text = total.convertForCoinBrazilian();
        }
    }

    private fun getColorTotal(valor: BigDecimal) : Int
    {
        if(valor >= BigDecimal.ZERO)
        {
            return incomeColor;
        }
        return expenseColor;
    }
}