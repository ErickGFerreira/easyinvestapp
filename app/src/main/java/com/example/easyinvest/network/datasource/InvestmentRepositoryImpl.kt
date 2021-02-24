package com.example.easyinvest.network.datasource

import com.example.easyinvest.investment.response.InvestmentResponse
import com.example.easyinvest.network.model.Result
import com.example.easyinvest.network.reposiories.InvestmentRepository
import com.example.easyinvest.network.retrofitWrapper
import com.example.easyinvest.network.service.InvestmentService

class InvestmentRepositoryImpl(private val service: InvestmentService): InvestmentRepository{


    override suspend fun simulateInvestiment(): Result<InvestmentResponse> {
        return retrofitWrapper {service.simulateInvestiment()}
    }

}