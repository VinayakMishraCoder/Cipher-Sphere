package com.example.ciphersphere_cryptocurrencytracker.datamodels

import com.google.gson.annotations.SerializedName

data class SingleCurrency (
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("name_full")
    val full_name: String? = null,

    @SerializedName("symbol")
    val symbol: String? = null,

    @SerializedName("icon_url")
    val url: String? = null,

    @SerializedName("max_supply")
    val maxSupply: String? = null
)