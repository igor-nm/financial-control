package com.igornm.financialcontrol.delegate

import com.igornm.financialcontrol.model.Transaction

/**
 * Created by igor on 26/12/17.
 */

interface TransactionDelegate
{
    fun delegate(transaction : Transaction);
}