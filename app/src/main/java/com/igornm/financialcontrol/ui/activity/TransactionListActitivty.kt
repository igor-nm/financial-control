package com.igornm.financialcontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.model.Transaction
import com.igornm.financialcontrol.model.Type
import com.igornm.financialcontrol.ui.AbstractView
import com.igornm.financialcontrol.ui.adapter.TransactionListAdapter
import com.igornm.financialcontrol.ui.dialog.AddTransactionDialog
import com.igornm.financialcontrol.ui.dialog.UpdateTransactionDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

/**
 * Created by igor on 25/12/17.
 */

class TransactionListActitivty: AppCompatActivity()
{
    private val transactions: MutableList<Transaction> = mutableListOf()
    private val viewActivity by lazy{ window.decorView }
    private val viewGroupActivity by lazy { viewActivity as ViewGroup }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        abstractSettings()
        listSettings()
        fabSettings()
    }

    private fun fabSettings()
    {
        lista_transacoes_adiciona_receita.setOnClickListener { showDialogTransactionAdd(Type.INCOME); }
        lista_transacoes_adiciona_despesa.setOnClickListener { showDialogTransactionAdd(Type.EXPENSE); }
    }

    private fun showDialogTransactionAdd(type: Type)
    {
        lista_transacoes_adiciona_menu.close(true)
        AddTransactionDialog(viewGroupActivity, this)
                .dialogSettings(type) { newTransaction ->
                    addTransaction(newTransaction)
                }

    }

    private fun addTransaction(transaction : Transaction)
    {
        transactions.add(transaction)
        updateTransactions()
    }

    private fun updateTransactions()
    {
        abstractSettings()
        listSettings()
    }

    private fun abstractSettings()
    {
        val abstractView = AbstractView(this, transactions, viewActivity)

        abstractView.update()
    }

    private fun listSettings()
    {
        val listTransactionAdapter = TransactionListAdapter(transactions, this)
        with(lista_transacoes_listview)
        {
            adapter = listTransactionAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transaction = transactions[position]
                showDialogTransactionUpdate(transaction, position)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item : MenuItem?) : Boolean
    {
        val itemId = item?.itemId
        if(itemId == 1)
        {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val position = adapterMenuInfo.position
            removeTransaction(position)
        }

        return super.onContextItemSelected(item)
    }

    private fun removeTransaction(position : Int)
    {
        transactions.removeAt(position)
        updateTransactions()
    }

    private fun showDialogTransactionUpdate(transaction : Transaction, position : Int)
    {
        UpdateTransactionDialog(viewGroupActivity, this).
                dialogSettings(transaction) {updatedTransaction ->
                    updateTransaction(updatedTransaction, position)
                }
    }

    private fun updateTransaction(transaction : Transaction, position : Int)
    {
        transactions[position] = transaction
        updateTransactions()
    }
}
