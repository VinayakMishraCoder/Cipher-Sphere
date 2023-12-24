package com.example.ciphersphere_cryptocurrencytracker.datamodels
import com.google.gson.annotations.SerializedName

data class CurrencyListingsResponse(
    @SerializedName("success")
    var success: Boolean? = null,

    @SerializedName("crypto")
    val crypto: Map<String, SingleCurrency>? = null,

    @SerializedName("fiat")
    val fiat: Map<String, String>? = null,
)