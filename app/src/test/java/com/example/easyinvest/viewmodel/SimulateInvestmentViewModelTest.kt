package com.example.easyinvest.viewmodel

import com.example.easyinvest.investment.response.InvestmentResponse
import com.example.easyinvest.investment.viewmodel.SimulateInvestmentViewModel
import com.example.easyinvest.network.model.Error
import com.example.easyinvest.network.model.Result
import com.example.easyinvest.network.reposiories.InvestmentRepository
import com.example.easyinvest.utils.InstantExecutorExtension
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantExecutorExtension::class)
@ExperimentalCoroutinesApi
class SimulateInvestmentViewModelTest {

    private val testCoroutineScope = TestCoroutineDispatcher()

    private val simulateInvestmentRepository = mockk<InvestmentRepository>()
    private lateinit var viewModel: SimulateInvestmentViewModel

    private val result = mockk<InvestmentResponse>(relaxed = true)
    private val error = mockk<Error>(relaxed = true)

    @BeforeEach
    fun setup() {
        viewModel = SimulateInvestmentViewModel(simulateInvestmentRepository, testCoroutineScope)
    }

    @AfterEach
    fun tearDownAfterEach() {
        clearAllMocks()
    }

    @Test
    fun `When calling simulateInvestment Should call simulateInvestment on Repository`() = runBlocking {
        coEvery { simulateInvestmentRepository.simulateInvestiment() } returns Result.success(result)
        viewModel.simulateInvestment()

        coVerify {
            simulateInvestmentRepository.simulateInvestiment()
        }
    }

    @Test
    fun `When calling simulateInvestment and receive Success, the successObserver must be filled with Data`() = runBlocking {
        coEvery { simulateInvestmentRepository.simulateInvestiment() } returns Result.success(result)
        viewModel.simulateInvestment()
        Assertions.assertNotNull(viewModel.simulateInvestmentLiveData.value)
        Assertions.assertNull(viewModel.errorLiveData.value)
    }

    @Test
    fun `When calling simulateInvestment and receive Error, the failureObserver must be filled with Error`() = runBlocking {
        coEvery { simulateInvestmentRepository.simulateInvestiment() } returns Result.failure(error)
        viewModel.simulateInvestment()
        Assertions.assertNull(viewModel.simulateInvestmentLiveData.value)
        Assertions.assertNotNull(viewModel.errorLiveData.value)
    }

}