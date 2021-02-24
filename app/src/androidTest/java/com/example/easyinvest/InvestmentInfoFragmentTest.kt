package com.example.easyinvest

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.easyinvest.investment.response.InvestmentResponse
import com.example.easyinvest.investment.ui.InvestmentActivity
import com.example.easyinvest.investment.ui.InvestmentInfoFragment
import com.example.easyinvest.network.model.Result
import com.example.easyinvest.network.reposiories.InvestmentRepository
import com.example.easyinvest.util.getDate
import com.example.easyinvest.util.getMoneyFormatter
import com.example.easyinvest.util.getPercentageString
import com.example.easyinvest.utils.DataSource
import com.example.easyinvest.utils.robot.ActionRobot
import com.example.easyinvest.utils.robot.ArrangeRobot
import com.example.easyinvest.utils.robot.AssertRobot
import io.mockk.coEvery
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
class InvestmentInfoFragmentTest : KoinTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val result = DataSource.investmentResponse
    lateinit var navController: NavController
    lateinit var mockModule: Module
    lateinit var mockedInteractor: InvestmentRepository
    lateinit var activityScenario: ActivityScenario<InvestmentActivity>
    lateinit var fragmentArgs: Bundle


    @Before
    fun before() {
        activityScenario = ActivityScenario.launch(InvestmentActivity::class.java)
        mockedInteractor = mockk()
        mockModule = module {
            factory(override = true) { mockedInteractor }
        }
        loadKoinModules(mockModule)
        coEvery { mockedInteractor.simulateInvestiment() } returns Result.success(result)
        setFragmentArgs()
        navController = ArrangeRobot.setViewNavConroler<InvestmentInfoFragment>(fragmentArgs)
    }

    private fun setFragmentArgs() {
        fragmentArgs = bundleOf("investmentInfo" to DataSource.investmentResponse)
    }

    @Test
    fun compareHeaderSectionTexts() {
        //Labels
        AssertRobot.compareText(
            R.id.investmentInfoInvestmentResultLabelTv,
            context.getString(R.string.investment_result_label)
        )
        //Infos
        fragmentArgs.getParcelable<InvestmentResponse>("investmentInfo")?.apply {
            AssertRobot.compareText(
                R.id.investmentInfoInvestmentResultTv,
                getMoneyFormatter(grossAmount)
            )
            AssertRobot.compareText(
                R.id.investmentInfoIncomeTv,
                String.format(
                    context.getString(R.string.yeld_result_label),
                    getMoneyFormatter(grossAmountProfit)
                )
            )
        }
    }

    @Test
    fun compareFirstSectionTexts() {
        //Labels
        AssertRobot.compareText(
            R.id.investmentInfoInitialValueLabelTv,
            context.getString(R.string.initial_value_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoTotalValueLabelTv,
            context.getString(R.string.total_value_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoIncomeValueLabelTv,
            context.getString(R.string.income_value_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoIRValueLabelTv,
            context.getString(R.string.ir_value_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoDiscountedValueLabelTv,
            context.getString(R.string.discounted_value_label)
        )

        //Infos
        fragmentArgs.getParcelable<InvestmentResponse>("investmentInfo")?.apply {
            AssertRobot.compareText(
                R.id.investmentInfoInitialValueTv,
                getMoneyFormatter(investmentParameter.investedAmount)
            )
            AssertRobot.compareText(
                R.id.investmentInfoTotalValueTv,
                getMoneyFormatter(grossAmount)
            )
            AssertRobot.compareText(
                R.id.investmentInfoIncomeValueTv,
                getMoneyFormatter(grossAmountProfit)
            )
            AssertRobot.compareText(
                R.id.investmentInfoIRValueTv,
                String.format("%s [%.2f%%]", getMoneyFormatter(taxesAmount), taxesRate)
            )
            AssertRobot.compareText(
                R.id.investmentInfoDiscountedValueTv,
                getMoneyFormatter(netAmount)
            )
        }

    }


    @Test
    fun compareSecondLabelTexts() {
        //Labels
        AssertRobot.compareText(
            R.id.investmentInfoWithdrawDateLabelTv,
            context.getString(R.string.withdraw_date_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoRunningDaysLabelTv,
            context.getString(R.string.running_days_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoMonthlyYeldLabelTv,
            context.getString(R.string.monthly_yeld_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoCDIPercentageLabelTv,
            context.getString(R.string.cdi_percentage_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoYearlyProfitabilityLabelTv,
            context.getString(R.string.year_profitability_label)
        )
        AssertRobot.compareText(
            R.id.investmentInfoProfitabilityPeriodicLabelTv,
            context.getString(R.string.profitability_periodic_label)
        )
        //Infos
        fragmentArgs.getParcelable<InvestmentResponse>("investmentInfo")?.apply {

            AssertRobot.compareText(
                R.id.investmentInfoWithdrawDateTv,
                getDate(investmentParameter.maturityDate, "yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy")
            )
            AssertRobot.compareText(
                R.id.investmentInfoMonthlyYeldTv,
                monthlyGrossRateProfit.getPercentageString()
            )

            AssertRobot.compareText(
                R.id.investmentInfoCDIPercentageTv,
                investmentParameter.rate.getPercentageString()
            )

            AssertRobot.compareText(
                R.id.investmentInfoYearlyProfitabilityTv,
                annualNetRateProfit.getPercentageString()
            )

            AssertRobot.compareText(
                R.id.investmentInfoProfitabilityPeriodicTv,
                rateProfit.getPercentageString()
            )
        }
    }


    @Test
    fun whenSimulateButtonAgainIsPressedShoulgBackToSimulateFragment() {
        ActionRobot.performClick(R.id.investmentInfoSimulatAgainButton)
        Assertions.assertEquals(
            navController.currentDestination?.id,
            R.id.simulateInvestmentFragment
        )
    }

    @Test
    fun checkIfButtonTextIsCorrect() {
        AssertRobot.compareText(
            R.id.investmentInfoSimulatAgainButton,
            context.getString(R.string.simulate_again)
        )
    }

    @Test
    fun checkIfTheButtonIsAlwaysEnabled(){
        AssertRobot.checkIfViewIsEnabled(R.id.investmentInfoSimulatAgainButton)
    }

    @AfterAll
    fun after() {
        stopKoin()
    }
}
