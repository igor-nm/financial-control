package com.igornm.financialcontrol.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.delegate.TrasactionDeletegate
import com.igornm.financialcontrol.extension.*
import com.igornm.financialcontrol.model.Transaction
import com.igornm.financialcontrol.model.Type
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.util.Calendar

/**
 * Created by igor on 26/12/17.
 */

class AddTransactionDialog(private val viewGroup : ViewGroup,
                           private val context : Context)
{
    private val viewChild  = layoutCreator();
    private val valueField = viewChild.form_transacao_valor;
    private val dateField  = viewChild.form_transacao_data;
    private val categoryField = viewChild.form_transacao_categoria;

    fun dialogSettings(type: Type, trasactionDeletegate : TrasactionDeletegate)
    {
        dateSettings();
        categorySettings(type);
        formSettings(type, trasactionDeletegate);
    }

    private fun formSettings(type: Type, trasactionDeletegate : TrasactionDeletegate)
    {
        val title = getTitle(type);

        AlertDialog.Builder(context)
                .setTitle(title)
                .setView(viewChild)
                .setPositiveButton("Adicionar", { _, _ ->
                    val value = valueField.text.toString().convertForBigDecimal();
                    val date = dateField.text.toString().convertForCalendar();
                    val category = categoryField.selectedItem.toString();

                    val transaction = Transaction(value, category, type, date);

                    trasactionDeletegate.delegate(transaction)
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private fun getTitle(type : Type) : Int
    {
        if(type == Type.INCOME)
        {
            return R.string.adiciona_receita;
        }
        return R.string.adiciona_despesa;
    }

    private fun getCategories(type : Type) : Int
    {
        if(type == Type.INCOME)
        {
            return R.array.categorias_de_receita;
        }
        return R.array.categorias_de_despesa
    }

    private fun categorySettings(type: Type)
    {
        val categories = getCategories(type);

        val adapter = ArrayAdapter
                .createFromResource(context,
                                    categories,
                                    android.R.layout.simple_spinner_dropdown_item);
        categoryField.adapter = adapter
    }

    private fun dateSettings()
    {
        val today = Calendar.getInstance();

        val year  = today.get(Calendar.YEAR);
        val month = today.get(Calendar.MONTH);
        val day   = today.get(Calendar.DAY_OF_MONTH);

        dateField.setText(today.calendarForString());
        dateField.setOnClickListener {
            DatePickerDialog(context,
                { _, year, month, day ->
                    val selectionDate = Calendar.getInstance();
                    selectionDate.set(year, month, day);
                    dateField.setText(selectionDate.calendarForString());
                }, year, month, day).show();
        }
    }

    private fun layoutCreator() : View
    {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.form_transacao,
                         viewGroup,
                         false);
    }
}