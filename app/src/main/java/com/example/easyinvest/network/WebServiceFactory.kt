package com.example.easyinvest.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WebServiceFactory {

    private val gson = GsonBuilder()
        .create()


    fun provideOkHttpClient(
    ): OkHttpClient {
        return provideOkHttpClientBuilder().build()
    }

    inline fun <reified T> createService(okHttpClient: OkHttpClient = provideOkHttpClient()): T {
        return defaultRetrofitBuilder(okHttpClient)
            .create(T::class.java)
    }

    fun defaultRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://teste.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @JvmStatic
    fun <T> createService(serviceClass: Class<T>, okHttpClient: OkHttpClient): T {
        return defaultRetrofitBuilder(okHttpClient)
            .create(serviceClass)
    }

    private fun provideOkHttpClientBuilder(
    ): OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

}