package com.example.easyinvest.util

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format: String): Date? {
    val sdf = SimpleDateFormat(format, Locale("pt", "BR"))
    return sdf.parse(this)
}

fun Date.format(format: String): String = SimpleDateFormat(format, Locale("pt", "BR")).format(this)

fun getDate(date: String, inputDateFormat: String, outputDateFormat: String): String {
    val dateFormatted = date.toDate(inputDateFormat)
    return dateFormatted?.format(outputDateFormat) ?: ""
}