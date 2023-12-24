package com.example.ciphersphere_cryptocurrencytracker.datamodels

data class FinalCurrencyData(
    /**
     * Will be put here from CurrencyListingsResponse.
     * */
    @Transient
    val currency: SingleCurrency? = null,

    /**
     * Will be put here from ExchangeRateResponse.
     * */
    @Transient
    val exchangeRate: String? = null
)