package com.example.easyinvest.network.service

import com.example.easyinvest.investment.response.InvestmentResponse
import retrofit2.http.GET

interface InvestmentService {


    @GET("https://run.mocky.io/v3/9f2ab28a-3591-4b7d-8cce-ebee3c212453")
    suspend fun simulateInvestiment(): InvestmentResponse
}
