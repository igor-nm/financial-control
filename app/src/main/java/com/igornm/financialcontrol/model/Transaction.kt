package com.igornm.financialcontrol.model

import java.math.BigDecimal
import java.util.Calendar

/**
 * Created by igor on 25/12/17.
 */

class Transaction(val value   : BigDecimal,
                  val category: String = "Undefined",
                  val type    : Type,
                  val date    : Calendar = Calendar.getInstance())