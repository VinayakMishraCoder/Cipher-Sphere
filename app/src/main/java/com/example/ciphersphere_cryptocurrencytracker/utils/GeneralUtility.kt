package com.example.ciphersphere_cryptocurrencytracker.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class GeneralUtility @Inject constructor(@ApplicationContext var context: Context) {

    fun isConnectedToInternet(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return ((capabilities != null) && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)))
    }

    companion object {
        fun roundUpToDecimalPlaces(scale: Int, number: String?): String {
            if (number == null) return "NA"
            try {
                val number = BigDecimal(number)
                val roundedNumber = number.setScale(scale, RoundingMode.CEILING)
                return roundedNumber.toPlainString()
            } catch (e: NumberFormatException) {
                // Handle the case where the input string is not a valid number
                return "NA"
            }
        }
    }
}