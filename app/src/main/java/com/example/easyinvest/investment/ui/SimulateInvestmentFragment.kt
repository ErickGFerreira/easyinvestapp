package com.example.easyinvest.investment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.easyinvest.databinding.FragmentSimulateInvestmentBinding
import com.example.easyinvest.investment.response.InvestmentResponse
import com.example.easyinvest.investment.viewmodel.SimulateInvestmentViewModel
import com.example.easyinvest.util.MaskUtils
import com.example.easyinvest.util.textwatchers.PercentTextWatcher
import com.example.easyinvest.util.afterTextChanged
import com.example.easyinvest.util.hideKeyboard
import com.example.easyinvest.util.textwatchers.MoneyTextWatcher
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimulateInvestmentFragment : Fragment() {

    private val dateMask by lazy {
        MaskUtils.insert(
            MaskUtils.DATE_MASK,
            binding?.simulateInvestmentDueDateEt
        )
    }

    private var binding: FragmentSimulateInvestmentBinding? = null

    private val viewModel: SimulateInvestmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSimulateInvestmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setUpTextWatchers()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupOservers()
    }

    private fun setupClickListeners() {
        binding?.simulateInvestmentSimulateBtn?.setOnClickListener {
            activity?.hideKeyboard(it)
            viewModel.simulateInvestment()
        }
    }

    private fun setUpTextWatchers() {
        binding?.simulateInvestmentAmountToInvestEt?.apply {
            afterTextChanged { amount ->
                viewModel.updateAmountToInvest(amount)
            }
            addTextChangedListener(MoneyTextWatcher(this))
        }

        binding?.simulateInvestmentCdiPercentageEt?.apply {
            afterTextChanged { CDI ->
                viewModel.updateCDIPercentage(CDI)
            }
            addTextChangedListener(
                PercentTextWatcher(
                    this
                )
            )
        }
        binding?.simulateInvestmentDueDateEt?.apply {
            afterTextChanged { dueDate ->
                viewModel.updateDueDate(dueDate)
            }
            addTextChangedListener(dateMask)
        }
    }


    private fun setupOservers() {
        investmentSimulatedWithSuccess()
        requestReturnedError()
        loadingBehavior()
        buttonBehavior()
    }

    private fun investmentSimulatedWithSuccess() {
        viewModel.simulateInvestmentLiveData.observe(
            viewLifecycleOwner,
            Observer { investmentResponse ->
                goToSimulateInfoFragment(investmentResponse)
            })
    }

    private fun goToSimulateInfoFragment(investmentResponse: InvestmentResponse) {
        val action =
            SimulateInvestmentFragmentDirections.actionSimulateInvestmentFragmentToInvestmentInfoFragment(
                investmentResponse
            )
        findNavController().navigate(action)
    }

    private fun requestReturnedError() {
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { error ->
            //show Dialog with error
        })
    }

    private fun loadingBehavior() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { onLoading ->
            binding?.simulateInvestmentLoadingFl?.isVisible = onLoading
        })
    }

    private fun buttonBehavior() {
        viewModel.buttonEnabled.observe(viewLifecycleOwner, Observer { buttonEnabled ->
            binding?.simulateInvestmentSimulateBtn?.isEnabled = buttonEnabled
        })
    }


}