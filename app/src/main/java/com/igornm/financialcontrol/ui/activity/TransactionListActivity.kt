package com.igornm.financialcontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.facebook.stetho.Stetho
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.database.dao.TransactionDAO
import com.igornm.financialcontrol.extension.calendarForString
import com.igornm.financialcontrol.extension.convertForCoinBrazilian
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

class TransactionListActivity : AppCompatActivity()
{
    private var transactions: MutableList<Transaction> = mutableListOf()
    private val viewActivity by lazy{ window.decorView }
    private val viewGroupActivity by lazy { viewActivity as ViewGroup }
    private val transactionDAO by lazy { TransactionDAO(this) }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        Stetho.initializeWithDefaults(this)

        val months = transactionDAO.findMoths()

        fabSettings()
        listSettings()
        abstractSettings()
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
                .dialogSettings(-1, type) { newTransaction ->
                    addTransaction(newTransaction)
                }

    }

    private fun addTransaction(transaction : Transaction)
    {
        transactionDAO.insertOrUpdate(transaction)
        updateTransactions()
    }

    private fun updateTransactions()
    {
        listSettings()
        abstractSettings()
    }

    private fun abstractSettings()
    {
        val abstractView = AbstractView(this, transactions, viewActivity)

        abstractView.update()
    }

    private fun listSettings()
    {
        transactions = transactionDAO.findAll()
        val listTransactionAdapter = TransactionListAdapter(transactions, this)
        with(lista_transacoes_listview)
        {
            adapter = listTransactionAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transaction = transactions[position]
                showDialogTransactionUpdate(transaction)
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
        val transaction = transactions[position]
        transactionDAO.deleteTransaction(transaction._id)
        updateTransactions()
    }

    private fun showDialogTransactionUpdate(transaction: Transaction)
    {
        UpdateTransactionDialog(viewGroupActivity, this).
                dialogSettings(transaction) {updatedTransaction ->
                    updateTransaction(updatedTransaction)
                }
    }

    private fun updateTransaction(transaction : Transaction)
    {
        transactionDAO.insertOrUpdate(transaction)
        updateTransactions()
    }
}
