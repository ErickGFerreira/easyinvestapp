package com.example.easyinvest.di

import com.example.easyinvest.investment.viewmodel.SimulateInvestmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        SimulateInvestmentViewModel(get())
    }
}