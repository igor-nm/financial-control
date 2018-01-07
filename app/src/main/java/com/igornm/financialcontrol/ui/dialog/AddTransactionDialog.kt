package com.igornm.financialcontrol.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.igornm.financialcontrol.R
import com.igornm.financialcontrol.model.Type

/**
 * Created by igor on 26/12/17.
 */

class AddTransactionDialog(viewGroup : ViewGroup,
                           context : Context): TransactionDialog(context, viewGroup)
{
    override val titlePositiveButton : String
        get() = "Adicionar"

    override fun getTitle(type : Type) : Int
    {
        if(type == Type.INCOME)
        {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }

}