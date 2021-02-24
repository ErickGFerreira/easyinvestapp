package com.example.easyinvest.util

import java.text.NumberFormat
import java.util.*

fun getMoneyFormatter(f: Float): String{
    return NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(f)
}