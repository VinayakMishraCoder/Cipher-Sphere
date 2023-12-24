package com.example.ciphersphere_cryptocurrencytracker.repositories

import com.example.ciphersphere_cryptocurrencytracker.datamodels.CurrencyListingsResponse
import com.example.ciphersphere_cryptocurrencytracker.datamodels.ExchangeRatesResponse
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val repoImpl: CryptoRepositoryImplementation) {

    suspend fun getCurrencyListings(): CurrencyListingsResponse = repoImpl.getCurrencyListings()

    suspend fun getExchangeRates(): ExchangeRatesResponse = repoImpl.getExchangeRates()

}
