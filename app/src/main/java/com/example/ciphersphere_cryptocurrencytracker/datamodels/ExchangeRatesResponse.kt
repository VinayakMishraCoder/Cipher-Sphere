package com.example.ciphersphere_cryptocurrencytracker.datamodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExchangeRatesResponse(
    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("rates")
    val rates: Map<String, String>? = null,

    @SerializedName("timestamp")
    val timestamp: Int? = null,

    @SerializedName("target")
    val target: String? = null,

    @SerializedName("terms")
    val terms: String? = null,

    @SerializedName("privacy")
    val privacy: String? = null
): Parcelable