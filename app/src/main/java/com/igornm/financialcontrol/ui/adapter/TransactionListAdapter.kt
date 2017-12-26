package com.igornm.financialcontrol.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.model.Transaction
import com.igornm.financialcontrol.extension.calendarForString
import com.igornm.financialcontrol.extension.convertForCoinBrazilian
import com.igornm.financialcontrol.extension.limitAtCharacter
import com.igornm.financialcontrol.model.Type
import kotlinx.android.synthetic.main.transacao_item.view.*

/**
 * Created by igor on 25/12/17.
 */

class TransactionListAdapter(private val transactions : List<Transaction>,
                             private val context : Context) : BaseAdapter()
{
    private val CHARACTER_LIMIT = 14;

    override fun getView(position : Int, view : View?, parent : ViewGroup?) : View
    {
        val newView = LayoutInflater
                .from(context).inflate(R.layout.transacao_item, parent, false);
        val transaction = transactions[position];

        addValue(transaction, newView)
        addIcon(transaction, newView)
        addCategory(newView, transaction)
        addDate(newView, transaction)

        return newView;
    }

    private fun addDate(newView : View, transaction : Transaction)
    {
        newView.transacao_data.text = transaction.date.calendarForString();
    }

    private fun addCategory(newView : View, transaction : Transaction)
    {
        newView.transacao_categoria.text = transaction.category.limitAtCharacter(CHARACTER_LIMIT);
    }

    private fun addIcon(transaction : Transaction, newView : View)
    {
        val icon = getIcon(transaction)
        newView.transacao_icone.setBackgroundResource(icon);
    }

    private fun getIcon(transaction : Transaction) : Int
    {
        if(transaction.type == Type.RECEITA)
        {
            return R.drawable.icone_transacao_item_receita;
        }
        return R.drawable.icone_transacao_item_despesa;
    }

    private fun addValue(transaction : Transaction, newView : View)
    {
        val color = getColor(transaction);
        newView.transacao_valor.setTextColor(color);
        newView.transacao_valor.text = transaction.value.convertForCoinBrazilian();
    }

    private fun getColor(transaction : Transaction) : Int
    {
        if(transaction.type == Type.RECEITA)
        {
            return ContextCompat.getColor(context, R.color.receita);
        }
        return ContextCompat.getColor(context, R.color.despesa);
    }

    override fun getItem(position : Int) : Transaction
    {
        return transactions[position];
    }

    override fun getItemId(p0 : Int) : Long
    {
        return 0;
    }

    override fun getCount() : Int
    {
        return transactions.size;
    }

}