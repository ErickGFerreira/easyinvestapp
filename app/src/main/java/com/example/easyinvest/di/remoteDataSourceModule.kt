package com.example.easyinvest.di

import com.example.easyinvest.network.WebServiceFactory
import com.example.easyinvest.network.service.InvestmentService
import org.koin.dsl.module

val remoteDataSourceModule = module {

    factory<InvestmentService> {
        WebServiceFactory.createService(WebServiceFactory.provideOkHttpClient())
    }
}