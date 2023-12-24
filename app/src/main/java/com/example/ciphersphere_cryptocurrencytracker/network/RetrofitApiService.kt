package com.example.ciphersphere_cryptocurrencytracker.network

import com.example.ciphersphere_cryptocurrencytracker.datamodels.CurrencyListingsResponse
import com.example.ciphersphere_cryptocurrencytracker.datamodels.ExchangeRatesResponse
import com.example.ciphersphere_cryptocurrencytracker.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("/live")
    suspend fun getExchangeRates(
        @Query("access_key") apiKey: String = Constants.SECRET_API_KEY
    ): ExchangeRatesResponse

    @GET("/list")
    suspend fun getCurrencyListings(
        @Query("access_key") apiKey: String = Constants.SECRET_API_KEY
    ): CurrencyListingsResponse

}