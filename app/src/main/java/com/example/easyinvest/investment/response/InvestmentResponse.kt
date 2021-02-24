package com.example.easyinvest.investment.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InvestmentResponse(
    @SerializedName("investmentParameter") val investmentParameter: InvestmentParameter,
    @SerializedName("grossAmount") val grossAmount: Float,
    @SerializedName("taxesAmount") val taxesAmount: Float,
    @SerializedName("netAmount") val netAmount: Float,
    @SerializedName("grossAmountProfit") val grossAmountProfit: Float,
    @SerializedName("netAmountProfit") val netAmountProfit: Float,
    @SerializedName("annualGrossRateProfit") val annualGrossRateProfit: Float,
    @SerializedName("monthlyGrossRateProfit") val monthlyGrossRateProfit: Float,
    @SerializedName("dailyGrossRateProfit") val dailyGrossRateProfit: Float,
    @SerializedName("taxesRate") val taxesRate: Float,
    @SerializedName("rateProfit") val rateProfit: Float,
    @SerializedName("annualNetRateProfit") val annualNetRateProfit: Float
):Parcelable


@Parcelize
data class InvestmentParameter(
    @SerializedName("investedAmount") val investedAmount: Float,
    @SerializedName("yearlyInterestRate") val yearlyInterestRate: Float,
    @SerializedName("maturityTotalDays") val maturityTotalDays: Int,
    @SerializedName("maturityBusinessDays") val maturityBusinessDays: Int,
    @SerializedName("maturityDate") val maturityDate: String,
    @SerializedName("rate") val rate: Float,
    @SerializedName("isTaxFree") val isTaxFree: Boolean
):Parcelable