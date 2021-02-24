package com.example.easyinvest.network.reposiories

import com.example.easyinvest.investment.response.InvestmentResponse
import com.example.easyinvest.network.model.Result

interface InvestmentRepository {

    suspend fun simulateInvestiment(): Result<InvestmentResponse>

}