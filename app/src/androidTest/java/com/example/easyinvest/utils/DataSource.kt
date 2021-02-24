package com.example.easyinvest.utils

import com.example.easyinvest.investment.response.InvestmentResponse
import com.google.gson.Gson

object DataSource {


    private const val response = "{\n" +
            "   \"investmentParameter\":{\n" +
            "      \"investedAmount\":32323.0,\n" +
            "      \"yearlyInterestRate\":9.5512,\n" +
            "      \"maturityTotalDays\":1981,\n" +
            "      \"maturityBusinessDays\":1409,\n" +
            "      \"maturityDate\":\"2023-03-03T00:00:00\",\n" +
            "      \"rate\":123.0,\n" +
            "      \"isTaxFree\":false\n" +
            "   },\n" +
            "   \"grossAmount\":60528.20,\n" +
            "   \"taxesAmount\":4230.78,\n" +
            "   \"netAmount\":56297.42,\n" +
            "   \"grossAmountProfit\":28205.20,\n" +
            "   \"netAmountProfit\":23974.42,\n" +
            "   \"annualGrossRateProfit\":87.26,\n" +
            "   \"monthlyGrossRateProfit\":0.76,\n" +
            "   \"dailyGrossRateProfit\":0.000445330025305748,\n" +
            "   \"taxesRate\":15.0,\n" +
            "   \"rateProfit\":9.5512,\n" +
            "   \"annualNetRateProfit\":74.17\n" +
            "}"

    val investmentResponse: InvestmentResponse =
        Gson().fromJson(response, InvestmentResponse::class.java)
}