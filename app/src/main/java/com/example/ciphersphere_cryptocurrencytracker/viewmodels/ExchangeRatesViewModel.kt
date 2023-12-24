package com.example.ciphersphere_cryptocurrencytracker.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ciphersphere_cryptocurrencytracker.datamodels.FinalCurrencyData
import com.example.ciphersphere_cryptocurrencytracker.datamodels.ResultWrapper
import com.example.ciphersphere_cryptocurrencytracker.repositories.CryptoRepository
import com.example.ciphersphere_cryptocurrencytracker.utils.DateTimeUtils
import com.example.ciphersphere_cryptocurrencytracker.utils.GeneralUtility
import com.example.ciphersphere_cryptocurrencytracker.utils.OnRefreshUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExchangeRatesViewModel @Inject constructor(
    private var repository: CryptoRepository,
    private var utility: GeneralUtility
) : ViewModel() {

    private val _responseData = MutableLiveData<ResultWrapper<ArrayList<FinalCurrencyData>>>()
    val responseData: LiveData<ResultWrapper<ArrayList<FinalCurrencyData>>> get() = _responseData

    var refreshTime = MutableLiveData<String>("Loading...")

    /**
     * Handles Loader meant to be shown when the data is called for the first time the app opens, the
     * retry button is clicked or when auto-refreshed. Not in the case of swipe refresh.
     * */
    var showLoader = MutableLiveData<Boolean>(false)

    /**
     *  Meant to show if there is currently any API call going on.
     * */
    var loading = MutableLiveData<Boolean>(false)

    /**
     * Meant to check if retry mechanism has come to halt.
     * */
    var retryCount = OnRefreshUtils.RETRY_COUNT

    init {
        retrieveCryptoData(true)
    }

    fun retrieveCryptoData(showLoaderStatus: Boolean) {
        if(loading.value == true) {
            /**
             * Page already loading, no need to refresh.
             * */
            return
        }
        if(!utility.isConnectedToInternet()) {
            _responseData.postValue(ResultWrapper.Error("Error : No Internet Connection detected."))
            return
        }
        viewModelScope.launch {
            if(showLoaderStatus) showLoader.value = true
            loading.value = true

            _responseData.postValue(ResultWrapper.Loading)
            /**
             * Combining data from both API calls.
             * */
            val result = withContext(Dispatchers.IO) {
                try {
                    val currencyListDeferred = async { repository.getCurrencyListings() }
                    val exchangeRatesDeferred = async { repository.getExchangeRates() }

                    val currencyList = currencyListDeferred.await()
                    val exchangeRates = exchangeRatesDeferred.await()

                    val finalDataList = exchangeRates.rates?.keys?.mapNotNull { symbol ->
                        var currency = currencyList.crypto?.get(symbol)
                        if (currency != null) {
                            FinalCurrencyData(currency, GeneralUtility.roundUpToDecimalPlaces(6, exchangeRates.rates[symbol]))
                        } else {
                            null
                        }
                    } as ArrayList<FinalCurrencyData>

                    ResultWrapper.Success(finalDataList)

                } catch (e: Exception) {
                    Log.e("error-msg", "Error : ${e.message}")

                    _responseData.postValue(ResultWrapper.Error("Error : ${e.message}"))
                    refreshTime.postValue("Error : ${e.message}")
                    ResultWrapper.Error("Error fetching data")
                }
            }

            if(result != null) {
                _responseData.postValue(result)
            }

            loading.value = false
            if(showLoaderStatus) showLoader.value = false

            /**
             * Set the value for latest refresh time.
             * */
            refreshTime.postValue(DateTimeUtils.getCurrentDateTime())
        }
    }
}