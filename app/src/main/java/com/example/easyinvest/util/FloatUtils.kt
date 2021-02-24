package com.example.easyinvest.util


fun Float.getPercentageString(): String{
    return String.format("%.2f%%", this)
}