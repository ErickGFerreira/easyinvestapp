package com.example.easyinvest.di

import com.example.easyinvest.network.datasource.InvestmentRepositoryImpl
import com.example.easyinvest.network.reposiories.InvestmentRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<InvestmentRepository> {
        InvestmentRepositoryImpl(get())
    }
}
