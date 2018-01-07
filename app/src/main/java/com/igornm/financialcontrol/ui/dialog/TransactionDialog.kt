package com.igornm.financialcontrol.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.extension.calendarForString
import com.igornm.financialcontrol.extension.convertForBigDecimal
import com.igornm.financialcontrol.extension.convertForCalendar
import com.igornm.financialcontrol.model.Transaction
import com.igornm.financialcontrol.model.Type
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.util.*

/**
 * Created by igor on 26/12/17.
 */

abstract class TransactionDialog(private val context : Context,
                             private val viewGroup : ViewGroup)
{
    private val viewChild  = layoutCreator()
    protected val valueField : EditText = viewChild.form_transacao_valor
    protected val dateField : EditText = viewChild.form_transacao_data
    protected val categoryField : Spinner = viewChild.form_transacao_categoria

    abstract protected val titlePositiveButton: String

    fun dialogSettings(type: Type, transactionDelegate : (transaction: Transaction) -> Unit)
    {
        dateSettings()
        categorySettings(type)
        formSettings(type, transactionDelegate)
    }


    private fun formSettings(type: Type, transactionDelegate : (transaction: Transaction) -> Unit)
    {
        val title = getTitle(type)

        AlertDialog.Builder(context)
                .setTitle(title)
                .setView(viewChild)
                .setPositiveButton(titlePositiveButton, { _, _ ->
                    val value = valueField.text.toString().convertForBigDecimal()
                    val date = dateField.text.toString().convertForCalendar()
                    val category = categoryField.selectedItem.toString()

                    val transaction = Transaction(value, category, type, date)

                    transactionDelegate(transaction)
                })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    abstract protected fun getTitle(type : Type) : Int

    protected fun getCategories(type : Type) : Int
    {
        if(type == Type.INCOME)
        {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun categorySettings(type: Type)
    {
        val categories = getCategories(type)

        val adapter = ArrayAdapter
                .createFromResource(context,
                                    categories,
                                    android.R.layout.simple_spinner_dropdown_item)
        categoryField.adapter = adapter
    }

    private fun dateSettings()
    {
        val today = Calendar.getInstance()

        val year  = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day   = today.get(Calendar.DAY_OF_MONTH)

        dateField.setText(today.calendarForString())
        dateField.setOnClickListener {
            DatePickerDialog(context,
                { _, year, month, day ->
                    val selectionDate = Calendar.getInstance()
                    selectionDate.set(year, month, day)
                    dateField.setText(selectionDate.calendarForString())
                }, year, month, day).show()
        }
    }

    private fun layoutCreator() : View
    {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.form_transacao,
                         viewGroup,
                         false)
    }
}