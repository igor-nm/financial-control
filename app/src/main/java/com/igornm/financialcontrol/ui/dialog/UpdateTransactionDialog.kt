package com.igornm.financialcontrol.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.extension.calendarForString
import com.igornm.financialcontrol.model.Transaction
import com.igornm.financialcontrol.model.Type

/**
 * Created by igor on 26/12/17.
 */

class UpdateTransactionDialog(
        viewGroup : ViewGroup,
        private val context : Context) : TransactionDialog(context, viewGroup)
{
    override val titlePositiveButton : String
        get() = "Alterar"

    fun dialogSettings(transaction : Transaction, transactionDelegate: (transition: Transaction) -> Unit)
    {
        val type = transaction.type
        val _id   = transaction._id

        super.dialogSettings(_id, type, transactionDelegate)

        initFields(transaction)
    }

    private fun initFields(transaction : Transaction)
    {
        initializeValueField(transaction)
        initializeFieldDate(transaction)
        initializeFieldCategory(transaction)
    }

    private fun initializeFieldCategory(transaction : Transaction)
    {
        val type = transaction.type
        val categories = context.resources.getStringArray(getCategories(type))
        val categoryPosition = categories.indexOf(transaction.category)
        categoryField.setSelection(categoryPosition, true)
    }

    private fun initializeFieldDate(transaction : Transaction)
    {
        dateField.setText(transaction.date.calendarForString())
    }

    private fun initializeValueField(transaction : Transaction)
    {
        valueField.setText(String.format("%s" , transaction.value))
    }

    override fun getTitle(type : Type) : Int
    {
        if(type == Type.INCOME)
        {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }
}