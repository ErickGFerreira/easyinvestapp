package com.example.easyinvest.base

import android.app.Application
import com.example.easyinvest.di.koinModules
import com.example.easyinvest.di.presentationModule
import com.example.easyinvest.di.remoteDataSourceModule
import com.example.easyinvest.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            modules(
               koinModules
            )
            androidContext(this@MainApplication)
        }
    }
}