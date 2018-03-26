package com.igornm.financialcontrol.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.igornm.financialcontrol.database.SQLiteHelper
import com.igornm.financialcontrol.extension.*
import com.igornm.financialcontrol.model.Transaction

/**
 * Created by igor on 1/28/18.
 */

class TransactionDAO(context: Context)
{
    private val database = SQLiteHelper(context).writableDatabase

    fun insertOrUpdate(transaction: Transaction)
    {
        val updates: Int

        val values = ContentValues()
        values.put(SQLiteHelper.TRANSACTION_VALUE, transaction.value.toString())
        values.put(SQLiteHelper.TRANSACTION_CATEGORY, transaction.category)
        values.put(SQLiteHelper.TRANSACTION_TYPE, transaction.type.toString())
        values.put(SQLiteHelper.TRANSACTION_DATE, (transaction.date.calendarForString()).convertForSQLite())

        updates = database.update(
                SQLiteHelper.TRANSACTION_TABLE,
                values,
                "${SQLiteHelper.TRANSACTION_ID} = ?",
                arrayOf(transaction._id.toString())
        )

        if(updates == 0)
        {
            database.insert(SQLiteHelper.TRANSACTION_TABLE, null, values)
        }
    }

    fun deleteTransaction(_id: Long)
    {
        database.delete(SQLiteHelper.TRANSACTION_TABLE, "${SQLiteHelper.TRANSACTION_ID} = ?", arrayOf(_id.toString()))
    }

    fun findMoths()
    {
        val months: MutableList<String> = mutableListOf()

        val cursor: Cursor = database.query(
                false,
                SQLiteHelper.TRANSACTION_TABLE,
                arrayOf("STRFTIME('%m', ${SQLiteHelper.TRANSACTION_DATE}) AS MONTH"),
                null, null, "MONTH", null, "MONTH DESC", null
        )

        if(cursor.count < 1)
        {
            cursor.close()
        }
        else
        {
            cursor.moveToFirst()

            while(!cursor.isAfterLast)
            {
                Log.i("FINANCIAL", "MONTH: ${cursor.getString(0)}")
                findAllForMonth(cursor.getString(0))
                cursor.moveToNext()
            }

            cursor.close()
            //return transactions
        }
    }

    fun findAllForMonth(month: String) : MutableList<Transaction>
    {
        val transactions: MutableList<Transaction> = mutableListOf()

        val cursor: Cursor = database.query(
                false,
                SQLiteHelper.TRANSACTION_TABLE,
                arrayOf(SQLiteHelper.TRANSACTION_ID, SQLiteHelper.TRANSACTION_VALUE, SQLiteHelper.TRANSACTION_CATEGORY,
                        SQLiteHelper.TRANSACTION_TYPE, SQLiteHelper.TRANSACTION_DATE),
                "STRFTIME('%m', ${SQLiteHelper.TRANSACTION_DATE}) in ('$month')", null, null, null, "${SQLiteHelper.TRANSACTION_DATE} DESC", null
        )

        if(cursor.count < 1)
        {
            cursor.close()
            return mutableListOf()
        }
        else
        {
            cursor.moveToFirst()

            while(!cursor.isAfterLast)
            {
                val transition: Transaction = cursorToTransaction(cursor)
                transactions.add(transition)
                Log.i("FINANCIAL", "Transation: ${transition._id}")
                cursor.moveToNext()
            }

            cursor.close()
            return transactions
        }
    }

    fun findAll() : MutableList<Transaction>
    {
        val transactions: MutableList<Transaction> = mutableListOf()

        val cursor: Cursor = database.query(
                false,
                SQLiteHelper.TRANSACTION_TABLE,
                arrayOf(SQLiteHelper.TRANSACTION_ID, SQLiteHelper.TRANSACTION_VALUE, SQLiteHelper.TRANSACTION_CATEGORY,
                        SQLiteHelper.TRANSACTION_TYPE, SQLiteHelper.TRANSACTION_DATE),
                null, null, null, null, null, null
        )

        if(cursor.count < 1)
        {
            cursor.close()
            return mutableListOf()
        }
        else
        {
            cursor.moveToFirst()

            while(!cursor.isAfterLast)
            {
                val transition: Transaction = cursorToTransaction(cursor)
                transactions.add(transition)
                cursor.moveToNext()
            }

            cursor.close()
            return transactions
        }
    }

    private fun cursorToTransaction(cursor: Cursor) : Transaction
    {
        return Transaction(
                cursor.getLong(0),
                cursor.getString(1).convertForBigDecimal(),
                cursor.getString(2),
                cursor.getString(3).convertForType(),
                cursor.getString(4).SQLiteForCalendar()
        )
    }
}