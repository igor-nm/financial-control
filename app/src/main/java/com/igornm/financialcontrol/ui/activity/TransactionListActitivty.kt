package com.igornm.financialcontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.model.Transaction
import com.igornm.financialcontrol.model.Type
import com.igornm.financialcontrol.ui.adapter.TransactionListAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

/**
 * Created by igor on 25/12/17.
 */

class TransactionListActitivty : AppCompatActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_transacoes);

        val transactions: List<Transaction> = exemplesTransactions();

        listSettings(transactions);
    }

    private fun exemplesTransactions() : List<Transaction>
    {
        return listOf(
                Transaction(BigDecimal(20.5), "Almoço de final de semana", Type.DESPESA),
                Transaction(BigDecimal(100.0), "Economia", Type.RECEITA),
                Transaction(BigDecimal(200.0), type = Type.DESPESA),
                Transaction(BigDecimal(1900.0), "Salário", Type.RECEITA));
    }

    private fun listSettings(transactions: List<Transaction>)
    {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transactions);

        lista_transacoes_listview.adapter = TransactionListAdapter(transactions, this);
    }
}