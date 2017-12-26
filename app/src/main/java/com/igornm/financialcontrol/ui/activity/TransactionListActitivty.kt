package com.igornm.financialcontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.delegate.TrasactionDeletegate
import com.igornm.financialcontrol.model.Transaction
import com.igornm.financialcontrol.model.Type
import com.igornm.financialcontrol.ui.AbstractView
import com.igornm.financialcontrol.ui.adapter.TransactionListAdapter
import com.igornm.financialcontrol.ui.dialog.AddTransactionDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

/**
 * Created by igor on 25/12/17.
 */

class TransactionListActitivty: AppCompatActivity()
{
    private val transactions: MutableList<Transaction> = mutableListOf();

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_transacoes);

        abstractSettings();
        listSettings();
        fabSettings()
    }

    private fun fabSettings()
    {
        lista_transacoes_adiciona_receita.setOnClickListener { showDialogTransaction(Type.RECEITA); };
        lista_transacoes_adiciona_despesa.setOnClickListener { showDialogTransaction(Type.DESPESA); };
    }

    private fun showDialogTransaction(type: Type)
    {
        lista_transacoes_adiciona_menu.close(true);
        AddTransactionDialog(window.decorView as ViewGroup, this)
                .dialogSettings(type, object: TrasactionDeletegate
                {
                    override fun delegate(trasaction : Transaction)
                    {
                        updateTransactions(trasaction);
                    }
                })
    }

    private fun updateTransactions(transaction : Transaction)
    {
        transactions.add(transaction);
        abstractSettings();
        listSettings();
    }

    private fun abstractSettings()
    {
        val view : View = window.decorView;
        val abstractView = AbstractView(this, transactions, view);

        abstractView.update()
    }

    private fun listSettings()
    {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transactions);

        lista_transacoes_listview.adapter = TransactionListAdapter(transactions, this);
    }
}