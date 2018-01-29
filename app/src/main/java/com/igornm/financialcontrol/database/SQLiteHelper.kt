package com.igornm.financialcontrol.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by igor on 1/28/18.
 */

class SQLiteHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, FACTORY, VERSION)
{
    companion object
    {
        private const val DATABASE_NAME = "financial.db"
        private const val VERSION = 1
        private val FACTORY = null

        const val TRANSACTION_TABLE        = "Transactions"
        const val TRANSACTION_ID           = "_id"
        const val TRANSACTION_VALUE        = "value"
        const val TRANSACTION_CATEGORY     = "category"
        const val TRANSACTION_TYPE         = "type"
        const val TRANSACTION_DATE         = "date"

        private val TRANSACTION_CREATE_TABLE = StringBuilder()
                .append("CREATE TABLE $TRANSACTION_TABLE (")
                .append("$TRANSACTION_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append("$TRANSACTION_VALUE TEXT,")
                .append("$TRANSACTION_CATEGORY TEXT,")
                .append("$TRANSACTION_TYPE TEXT,")
                .append("$TRANSACTION_DATE TEXT);")
                .toString()
    }

    override fun onCreate(database: SQLiteDatabase?)
    {
        database?.execSQL(TRANSACTION_CREATE_TABLE)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        database?.execSQL(dropTable(TRANSACTION_TABLE))
    }

    private fun dropTable(table : String) : String
    {
        return "DROP TABLE IF EXISTS $table"
    }
}