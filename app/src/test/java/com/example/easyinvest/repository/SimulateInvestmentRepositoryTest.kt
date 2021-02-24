package com.example.easyinvest.repository

import com.example.easyinvest.investment.response.InvestmentResponse
import com.example.easyinvest.network.datasource.InvestmentRepositoryImpl
import com.example.easyinvest.network.service.InvestmentService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SimulateInvestmentRepositoryTest {

    private val investmentService = mockk<InvestmentService>()
    private val investmentResponse = mockk<InvestmentResponse>()

    private val subject = InvestmentRepositoryImpl(
        investmentService
    )


    @Nested
    inner class `Given a investmentService` {

        @Test
        fun `When calling simulateInvestment Should call simulateInvestment on investmentService`() =
            runBlocking {
                coEvery { investmentService.simulateInvestiment() } returns investmentResponse
                subject.simulateInvestiment()

                coVerify {
                    investmentService.simulateInvestiment()
                }
            }
    }


}