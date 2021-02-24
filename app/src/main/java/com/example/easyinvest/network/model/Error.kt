package com.example.easyinvest.network.model

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("code") val code: Int,
    @SerializedName("title") val title: String,
    @SerializedName("Message") val message: String
)