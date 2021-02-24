package com.example.easyinvest

import androidx.navigation.NavController
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.easyinvest.investment.ui.InvestmentActivity
import com.example.easyinvest.investment.ui.SimulateInvestmentFragment
import com.example.easyinvest.network.model.Result
import com.example.easyinvest.network.reposiories.InvestmentRepository
import com.example.easyinvest.utils.DataSource
import com.example.easyinvest.utils.robot.ActionRobot
import com.example.easyinvest.utils.robot.ArrangeRobot
import com.example.easyinvest.utils.robot.AssertRobot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
@LargeTest
class SimulateInvestmentFragmentTest : KoinTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val result = DataSource.investmentResponse
    lateinit var navController: NavController
    lateinit var mockModule: Module
    lateinit var mockedInteractor: InvestmentRepository
    lateinit var activityScenario: ActivityScenario<InvestmentActivity>


    @Before
    fun before() {
        activityScenario = ActivityScenario.launch(InvestmentActivity::class.java)
        mockedInteractor = mockk()
        mockModule = module {
            factory(override = true) { mockedInteractor }
        }
        loadKoinModules(mockModule)
        coEvery { mockedInteractor.simulateInvestiment() } returns Result.success(result)
        navController = ArrangeRobot.setViewNavConroler<SimulateInvestmentFragment>()
    }

    @Test
    fun compareTexts() {
        AssertRobot.compareText(
            R.id.simulateInvestmentAmountToInvestTv,
            context.getString(R.string.amount_to_invest_field)
        )
        AssertRobot.compareText(
            R.id.simulateInvestmentDueDateTv,
            context.getString(R.string.duedate_field)
        )
        AssertRobot.compareText(
            R.id.simulateInvestmentCdiPercentageTv,
            context.getString(R.string.cdi_percentage_field)
        )
    }

    @Test
    fun compareHints() {
        AssertRobot.compareHint(
            R.id.simulateInvestmentAmountToInvestEt,
            context.getString(R.string.amount_to_invest_hint)
        )
        AssertRobot.compareHint(
            R.id.simulateInvestmentDueDateEt,
            context.getString(R.string.duedate_hint)
        )
        AssertRobot.compareHint(
            R.id.simulateInvestmentCdiPercentageEt,
            context.getString(R.string.cdi_percentage_hint)
        )
    }

    @Test
    fun ifAllFieldsAreFilledAndSimulateButtonIsClickedShouldMakeSimulateRequestAndGoToNextScreen() {
        fillTexts()
        ActionRobot.performClick(R.id.simulateInvestmentSimulateBtn)
        coVerify { mockedInteractor.simulateInvestiment() }
        Assertions.assertEquals(navController.currentDestination?.id, R.id.investmentInfoFragment)
    }

    @Test
    fun ifAllFieldsAreNotFilledAndSimulateButtonIsClickedShouldNottMakeTheRequest() {
        ActionRobot.performClick(R.id.simulateInvestmentSimulateBtn)
        Assertions.assertNotEquals(
            navController.currentDestination?.id,
            R.id.investmentInfoFragment
        )
    }

    @Test
    fun ifDueDateIsNotValidSimulateButtonMustBeDisabled() {
        ActionRobot.typeText(R.id.simulateInvestmentDueDateEt, "12132012")
        AssertRobot.checkIfViewIsDisabled(R.id.simulateInvestmentSimulateBtn)
    }

    @Test
    fun ifAmountToInvestIsInvalidSimulateButtonMustBeDisabled() {
        ActionRobot.typeText(R.id.simulateInvestmentAmountToInvestEt, "0")
        AssertRobot.checkIfViewIsDisabled(R.id.simulateInvestmentSimulateBtn)
    }


    @Test
    fun ifCDIPercentageIsInvalidSimulateButtonMustBeDisabled() {
        ActionRobot.typeText(R.id.simulateInvestmentCdiPercentageEt, "0")
        AssertRobot.checkIfViewIsDisabled(R.id.simulateInvestmentSimulateBtn)
    }

    private fun fillTexts() {
        ActionRobot.typeText(R.id.simulateInvestmentAmountToInvestEt, "30000")
        ActionRobot.typeText(R.id.simulateInvestmentDueDateEt, "12122012")
        ActionRobot.typeText(R.id.simulateInvestmentCdiPercentageEt, "300")
    }


    @Test
    fun checkIfButtonTextIsCorrect() {
        AssertRobot.compareText(
            R.id.simulateInvestmentSimulateBtn,
            context.getString(R.string.simulate_button)
        )
    }

    @AfterAll
    fun after() {
        stopKoin()
    }
}