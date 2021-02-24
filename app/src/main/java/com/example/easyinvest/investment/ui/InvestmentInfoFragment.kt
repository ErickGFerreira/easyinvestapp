package com.example.easyinvest.investment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.easyinvest.databinding.FragmentInvestmentInfoBinding
import com.example.easyinvest.investment.response.InvestmentResponse
import com.example.easyinvest.util.*

class InvestmentInfoFragment : Fragment() {
    private val args: InvestmentInfoFragmentArgs by navArgs()
    private var binding: FragmentInvestmentInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvestmentInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupInvestmentInfos(args.investmentInfo)
    }

    private fun setupClickListener() {
        binding?.investmentInfoSimulatAgainButton?.setOnClickListener { goToSimulateFragment() }
    }

    private fun setupInvestmentInfos(investmentResponse: InvestmentResponse) =
        with(investmentResponse) {
            binding?.investmentInfoInvestmentResultTv?.text = getMoneyFormatter(grossAmount)
            binding?.investmentInfoIncomeTv?.text = String.format("Rendimento total de %s", getMoneyFormatter(grossAmountProfit))
            binding?.investmentInfoInitialValueTv?.text =
                getMoneyFormatter(investmentParameter.investedAmount)
            binding?.investmentInfoTotalValueTv?.text = getMoneyFormatter(grossAmount)
            binding?.investmentInfoIncomeValueTv?.text = getMoneyFormatter(grossAmountProfit)
            binding?.investmentInfoIRValueTv?.text =
                String.format("%s [%.2f%%]", getMoneyFormatter(taxesAmount), taxesRate)
            binding?.investmentInfoDiscountedValueTv?.text = getMoneyFormatter(netAmount)
            binding?.investmentInfoWithdrawDateTv?.text =
                getDate(investmentParameter.maturityDate, "yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy")
            binding?.investmentInfoRunningDaysTv?.text =
                investmentParameter.maturityTotalDays.toString()
            binding?.investmentInfoMonthlyYeldTv?.text =
                monthlyGrossRateProfit.getPercentageString()
            binding?.investmentInfoCDIPercentageTv?.text =
                investmentParameter.rate.getPercentageString()
            binding?.investmentInfoYearlyProfitabilityTv?.text =
                annualNetRateProfit.getPercentageString()
            binding?.investmentInfoProfitabilityPeriodicTv?.text = rateProfit.getPercentageString()
        }




    private fun goToSimulateFragment() {
        val action =
            InvestmentInfoFragmentDirections.actionInvestmentInfoFragmentToSimulateInvestmentFragment()
        findNavController().navigate(action)
    }

}