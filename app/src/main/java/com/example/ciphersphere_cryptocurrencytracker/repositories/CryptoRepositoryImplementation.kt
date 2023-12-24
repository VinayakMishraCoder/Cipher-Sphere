package com.example.ciphersphere_cryptocurrencytracker.repositories

import com.example.ciphersphere_cryptocurrencytracker.datamodels.CurrencyListingsResponse
import com.example.ciphersphere_cryptocurrencytracker.datamodels.ExchangeRatesResponse
import com.example.ciphersphere_cryptocurrencytracker.network.RetrofitApiService
import javax.inject.Inject

class CryptoRepositoryImplementation @Inject constructor(private val coinLayerApiService: RetrofitApiService) {

    suspend fun getCurrencyListings(): CurrencyListingsResponse {
        return coinLayerApiService.getCurrencyListings()
    }

    suspend fun getExchangeRates(): ExchangeRatesResponse {
        return coinLayerApiService.getExchangeRates()
    }
}