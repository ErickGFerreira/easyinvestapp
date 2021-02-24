package com.example.easyinvest.util


fun String.justDigits(): String {
    return this.replace("[^0-9]".toRegex(), "")
}
